from flask import Flask, render_template, jsonify, request
import json
import os
import uuid

app = Flask(__name__)

sessoes = {}

EXERCICIOS = {
    "peito": {
        "icon": "🏋️",
        "exercicios": {
            "forca": [
                {"nome": "Supino Reto com Barra", "series": "4x8-10", "descanso": "90s", "aparelho": "Barra + Banco Reto", "casa": "Flexão de braços (4x12)"},
                {"nome": "Supino Inclinado com Halteres", "series": "4x10-12", "descanso": "75s", "aparelho": "Halteres + Banco Inclinado", "casa": "Flexão inclinada (4x10)"},
                {"nome": "Crucifixo na Máquina", "series": "3x12-15", "descanso": "60s", "aparelho": "Máquina Peck Deck", "casa": "Crucifixo com garrafas (3x15)"},
                {"nome": "Crossover", "series": "3x12-15", "descanso": "60s", "aparelho": "Polia Alta", "casa": "Flexão aberta (3x12)"},
                {"nome": "Supino Reto com Halteres", "series": "4x10-12", "descanso": "75s", "aparelho": "Halteres + Banco", "casa": "Flexão diamante (4x10)"}
            ],
            "definicao": [
                {"nome": "Supino Reto com Halteres", "series": "3x12-15", "descanso": "60s", "aparelho": "Halteres", "casa": "Flexão de braços (3x15)"},
                {"nome": "Crossover Baixo", "series": "3x15-20", "descanso": "45s", "aparelho": "Polia Baixa", "casa": "Flexão aberta (3x20)"},
                {"nome": "Supino Inclinado com Halteres", "series": "3x12-15", "descanso": "60s", "aparelho": "Halteres + Banco", "casa": "Flexão inclinada (3x15)"},
                {"nome": "Crucifixo no Chão", "series": "3x15-20", "descanso": "45s", "aparelho": "Halteres + Chão", "casa": "Crucifixo no chão (3x20)"},
                {"nome": "Máquina Peck Deck", "series": "3x15-20", "descanso": "45s", "aparelho": "Peck Deck", "casa": "Flexão com pausa (3x15)"}
            ],
            "perda_peso": [
                {"nome": "Supino Reto com Barra", "series": "4x12-15", "descanso": "45s", "aparelho": "Barra + Banco", "casa": "Flexão de braços (4x20)"},
                {"nome": "Burpee com Flexão", "series": "3x12-15", "descanso": "30s", "aparelho": "Peso corporal", "casa": "Burpee (3x12)"},
                {"nome": "Crossover", "series": "3x15-20", "descanso": "30s", "aparelho": "Polia", "casa": "Flexão explosiva (3x15)"},
                {"nome": "Flexão com Palma", "series": "3x10-12", "descanso": "30s", "aparelho": "Peso corporal", "casa": "Flexão com palma (3x10)"},
                {"nome": "Supino Inclinado com Halteres", "series": "3x15-20", "descanso": "30s", "aparelho": "Halteres", "casa": "Flexão inclinada (3x20)"}
            ]
        }
    },
    "costas": {
        "icon": "💪",
        "exercicios": {
            "forca": [
                {"nome": "Puxada Frontal", "series": "4x8-10", "descanso": "90s", "aparelho": "Máquina Puxada", "casa": "Remada com garrafa (4x10)"},
                {"nome": "Remada Curvada com Barra", "series": "4x8-10", "descanso": "90s", "aparelho": "Barra", "casa": "Remada com garrafa (4x10)"},
                {"nome": "Remada Unilateral com Halter", "series": "3x10-12", "descanso": "75s", "aparelho": "Halter", "casa": "Remada com garrafa (3x12)"},
                {"nome": "Pulldown com Corda", "series": "3x12-15", "descanso": "60s", "aparelho": "Polia", "casa": "Prancha com remada (3x12)"},
                {"nome": "Remada Sentado na Máquina", "series": "4x10-12", "descanso": "75s", "aparelho": "Máquina Remada", "casa": "Superman (4x12)"}
            ],
            "definicao": [
                {"nome": "Puxada Frontal", "series": "3x12-15", "descanso": "60s", "aparelho": "Máquina Puxada", "casa": "Remada com garrafa (3x15)"},
                {"nome": "Remada Curvada com Halteres", "series": "3x12-15", "descanso": "60s", "aparelho": "Halteres", "casa": "Remada com garrafa (3x15)"},
                {"nome": "Pulldown na Máquina", "series": "3x15-20", "descanso": "45s", "aparelho": "Máquina", "casa": "Prancha (3x45s)"},
                {"nome": "Remada Cavalinho", "series": "3x12-15", "descanso": "60s", "aparelho": "Máquina", "casa": "Superman (3x15)"},
                {"nome": "Pullover na Polia", "series": "3x15-20", "descanso": "45s", "aparelho": "Polia", "casa": "Prancha lateral (3x30s)"}
            ],
            "perda_peso": [
                {"nome": "Remada Curvada com Barra", "series": "4x12-15", "descanso": "45s", "aparelho": "Barra", "casa": "Remada com garrafa (4x20)"},
                {"nome": "Burpee", "series": "3x12-15", "descanso": "30s", "aparelho": "Peso corporal", "casa": "Burpee (3x12)"},
                {"nome": "Puxada Frontal", "series": "3x15-20", "descanso": "30s", "aparelho": "Máquina", "casa": "Remada explosiva (3x15)"},
                {"nome": "Mountain Climber", "series": "3x20-30", "descanso": "30s", "aparelho": "Peso corporal", "casa": "Mountain climber (3x20)"},
                {"nome": "Remada Unilateral", "series": "3x15-20", "descanso": "30s", "aparelho": "Halter", "casa": "Remada com garrafa (3x20)"}
            ]
        }
    },
    "pernas": {
        "icon": "🦵",
        "exercicios": {
            "forca": [
                {"nome": "Agachamento Livre", "series": "4x8-10", "descanso": "120s", "aparelho": "Barra + Rack", "casa": "Agachamento livre (4x12)"},
                {"nome": "Leg Press 45°", "series": "4x10-12", "descanso": "90s", "aparelho": "Leg Press", "casa": "Agachamento com salto (4x10)"},
                {"nome": "Cadeira Extensora", "series": "3x12-15", "descanso": "60s", "aparelho": "Máquina", "casa": "Agachamento isométrico (3x45s)"},
                {"nome": "Mesa Flexora", "series": "3x12-15", "descanso": "60s", "aparelho": "Máquina", "casa": "Cadeira romana (3x12)"},
                {"nome": "Panturrilha em Pé", "series": "4x15-20", "descanso": "45s", "aparelho": "Máquina", "casa": "Panturrilha no degrau (4x20)"}
            ],
            "definicao": [
                {"nome": "Agachamento Livre", "series": "3x12-15", "descanso": "60s", "aparelho": "Barra", "casa": "Agachamento livre (3x20)"},
                {"nome": "Leg Press 45°", "series": "3x15-20", "descanso": "45s", "aparelho": "Leg Press", "casa": "Agachamento com salto (3x15)"},
                {"nome": "Cadeira Extensora", "series": "3x15-20", "descanso": "45s", "aparelho": "Máquina", "casa": "Wall sit (3x45s)"},
                {"nome": "Mesa Flexora", "series": "3x15-20", "descanso": "45s", "aparelho": "Máquina", "casa": "Cadeira romana (3x15)"},
                {"nome": "Panturrilha em Pé", "series": "3x20-25", "descanso": "30s", "aparelho": "Máquina", "casa": "Panturrilha no degrau (3x25)"}
            ],
            "perda_peso": [
                {"nome": "Agachamento com Salto", "series": "4x12-15", "descanso": "30s", "aparelho": "Peso corporal", "casa": "Agachamento com salto (4x15)"},
                {"nome": "Leg Press 45°", "series": "4x15-20", "descanso": "30s", "aparelho": "Leg Press", "casa": "Burpee (4x12)"},
                {"nome": "Avanço com Halteres", "series": "3x15-20", "descanso": "30s", "aparelho": "Halteres", "casa": "Avanço alternado (3x20)"},
                {"nome": "Agachamento Isométrico", "series": "3x45-60s", "descanso": "30s", "aparelho": "Peso corporal", "casa": "Wall sit (3x60s)"},
                {"nome": "Panturrilha Explosiva", "series": "3x20-25", "descanso": "30s", "aparelho": "Peso corporal", "casa": "Panturrilha com salto (3x20)"}
            ]
        }
    },
    "ombros": {
        "icon": "🎯",
        "exercicios": {
            "forca": [
                {"nome": "Desenvolvimento com Barra", "series": "4x8-10", "descanso": "90s", "aparelho": "Barra", "casa": "Desenvolvimento com garrafas (4x10)"},
                {"nome": "Elevação Lateral com Halteres", "series": "3x10-12", "descanso": "60s", "aparelho": "Halteres", "casa": "Elevação lateral com garrafas (3x12)"},
                {"nome": "Face Pull", "series": "3x12-15", "descanso": "60s", "aparelho": "Polia", "casa": "Elevação frontal (3x12)"},
                {"nome": "Arnold Press", "series": "3x10-12", "descanso": "75s", "aparelho": "Halteres", "casa": "Desenvolvimento com garrafas (3x12)"},
                {"nome": "Elevação Frontal com Barra", "series": "3x12-15", "descanso": "60s", "aparelho": "Barra", "casa": "Elevação frontal com garrafa (3x15)"}
            ],
            "definicao": [
                {"nome": "Desenvolvimento com Halteres", "series": "3x12-15", "descanso": "60s", "aparelho": "Halteres", "casa": "Desenvolvimento com garrafas (3x15)"},
                {"nome": "Elevação Lateral", "series": "3x15-20", "descanso": "45s", "aparelho": "Halteres leves", "casa": "Elevação lateral com garrafas (3x20)"},
                {"nome": "Face Pull na Polia", "series": "3x15-20", "descanso": "45s", "aparelho": "Polia", "casa": "Prancha com elevação (3x12)"},
                {"nome": "Arnold Press", "series": "3x12-15", "descanso": "60s", "aparelho": "Halteres", "casa": "Desenvolvimento (3x15)"},
                {"nome": "Elevação Posterior", "series": "3x15-20", "descanso": "45s", "aparelho": "Halteres", "casa": "Elevação inclinada (3x15)"}
            ],
            "perda_peso": [
                {"nome": "Desenvolvimento com Halteres", "series": "3x15-20", "descanso": "30s", "aparelho": "Halteres", "casa": "Desenvolvimento com garrafas (3x20)"},
                {"nome": "Elevação Lateral", "series": "3x20-25", "descanso": "30s", "aparelho": "Halteres leves", "casa": "Elevação lateral (3x25)"},
                {"nome": "Burpee com Elevação", "series": "3x12-15", "descanso": "30s", "aparelho": "Peso corporal", "casa": "Burpee (3x12)"},
                {"nome": "Mountain Climber", "series": "3x20-30", "descanso": "30s", "aparelho": "Peso corporal", "casa": "Mountain climber (3x20)"},
                {"nome": "Elevação Frontal", "series": "3x20-25", "descanso": "30s", "aparelho": "Halteres", "casa": "Elevação frontal (3x25)"}
            ]
        }
    },
    "bracos": {
        "icon": "💪",
        "exercicios": {
            "forca": [
                {"nome": "Rosca Direta com Barra", "series": "4x8-10", "descanso": "75s", "aparelho": "Barra", "casa": "Rosca com garrafa (4x10)"},
                {"nome": "Tríceps Testa com Barra", "series": "4x8-10", "descanso": "75s", "aparelho": "Barra", "casa": "Tríceps no chão (4x10)"},
                {"nome": "Rosca Alternada com Halteres", "series": "3x10-12", "descanso": "60s", "aparelho": "Halteres", "casa": "Rosca com garrafa (3x12)"},
                {"nome": "Tríceps na Polia", "series": "3x10-12", "descanso": "60s", "aparelho": "Polia", "casa": "Mergulho na cadeira (3x12)"},
                {"nome": "Rosca Martelo", "series": "3x12-15", "descanso": "60s", "aparelho": "Halteres", "casa": "Rosca com garrafa (3x15)"}
            ],
            "definicao": [
                {"nome": "Rosca Direta com Barra", "series": "3x12-15", "descanso": "60s", "aparelho": "Barra", "casa": "Rosca com garrafa (3x15)"},
                {"nome": "Tríceps Testa", "series": "3x12-15", "descanso": "60s", "aparelho": "Halteres", "casa": "Tríceps no chão (3x15)"},
                {"nome": "Rosca Alternada", "series": "3x15-20", "descanso": "45s", "aparelho": "Halteres", "casa": "Rosca com garrafa (3x20)"},
                {"nome": "Tríceps na Polia", "series": "3x15-20", "descanso": "45s", "aparelho": "Polia", "casa": "Mergulho (3x15)"},
                {"nome": "Rosca Martelo", "series": "3x15-20", "descanso": "45s", "aparelho": "Halteres", "casa": "Rosca com garrafa (3x20)"}
            ],
            "perda_peso": [
                {"nome": "Rosca com Burpee", "series": "3x12-15", "descanso": "30s", "aparelho": "Peso corporal", "casa": "Burpee (3x12)"},
                {"nome": "Tríceps no Chão", "series": "3x15-20", "descanso": "30s", "aparelho": "Peso corporal", "casa": "Tríceps no chão (3x20)"},
                {"nome": "Rosca Alternada", "series": "3x15-20", "descanso": "30s", "aparelho": "Halteres", "casa": "Rosca com garrafa (3x20)"},
                {"nome": "Mergulho na Cadeira", "series": "3x15-20", "descanso": "30s", "aparelho": "Cadeira", "casa": "Mergulho (3x15)"},
                {"nome": "Flexão de Braços", "series": "3x12-15", "descanso": "30s", "aparelho": "Peso corporal", "casa": "Flexão (3x15)"}
            ]
        }
    },
    "abdomen": {
        "icon": "🎯",
        "exercicios": {
            "forca": [
                {"nome": "Abdominal Infra no Banco", "series": "4x12-15", "descanso": "60s", "aparelho": "Banco", "casa": "Elevação de pernas (4x15)"},
                {"nome": "Prancha Frontal", "series": "3x45-60s", "descanso": "45s", "aparelho": "Peso corporal", "casa": "Prancha (3x60s)"},
                {"nome": "Russian Twist com Halter", "series": "3x15-20", "descanso": "45s", "aparelho": "Halter", "casa": "Russian Twist (3x20)"},
                {"nome": "Abdominal no Solo", "series": "4x15-20", "descanso": "45s", "aparelho": "Peso corporal", "casa": "Abdominal (4x20)"},
                {"nome": "Elevação de Pernas na Barra", "series": "3x12-15", "descanso": "60s", "aparelho": "Barra Fixa", "casa": "Elevação de pernas no chão (3x15)"}
            ],
            "definicao": [
                {"nome": "Abdominal Crunch no Solo", "series": "3x20-25", "descanso": "30s", "aparelho": "Peso corporal", "casa": "Crunch (3x25)"},
                {"nome": "Prancha Frontal", "series": "3x60-90s", "descanso": "30s", "aparelho": "Peso corporal", "casa": "Prancha (3x90s)"},
                {"nome": "Russian Twist", "series": "3x20-30", "descanso": "30s", "aparelho": "Peso corporal", "casa": "Russian Twist (3x30)"},
                {"nome": "Abdominal Infra", "series": "3x20-25", "descanso": "30s", "aparelho": "Peso corporal", "casa": "Elevação de pernas (3x25)"},
                {"nome": "Bicicleta no Chão", "series": "3x20-30", "descanso": "30s", "aparelho": "Peso corporal", "casa": "Bicicleta (3x30)"}
            ],
            "perda_peso": [
                {"nome": "Burpee", "series": "4x12-15", "descanso": "30s", "aparelho": "Peso corporal", "casa": "Burpee (4x12)"},
                {"nome": "Mountain Climber", "series": "3x20-30", "descanso": "30s", "aparelho": "Peso corporal", "casa": "Mountain climber (3x30)"},
                {"nome": "Prancha com Toque no Ombro", "series": "3x15-20", "descanso": "30s", "aparelho": "Peso corporal", "casa": "Prancha com toque (3x20)"},
                {"nome": "Abdominal Bicycle", "series": "3x20-30", "descanso": "30s", "aparelho": "Peso corporal", "casa": "Bicicleta (3x30)"},
                {"nome": "High Knees", "series": "3x30-40", "descanso": "30s", "aparelho": "Peso corporal", "casa": "Elevação de joelhos (3x30)"}
            ]
        }
    }
}

SEMANA_TREINO = {
    0: {"dia": "Segunda", "grupo": "peito", "treino": "Peito"},
    1: {"dia": "Terca", "grupo": "costas", "treino": "Costas"},
    2: {"dia": "Quarta", "grupo": "pernas", "treino": "Pernas"},
    3: {"dia": "Quinta", "grupo": "ombros", "treino": "Ombros"},
    4: {"dia": "Sexta", "grupo": "bracos", "treino": "Bracos"},
    5: {"dia": "Sabado", "grupo": "pernas", "treino": "Pernas"},
    6: {"dia": "Domingo", "grupo": None, "treino": "Descanso"}
}

@app.route("/")
def index():
    return render_template("index.html")

@app.route("/menu")
def menu():
    return render_template("menu.html")

@app.route("/cadastro")
def cadastro():
    return render_template("cadastro.html")

@app.route("/treino/<grupo>")
def treino(grupo):
    return render_template("treino.html", grupo=grupo)

@app.route("/semana")
def semana():
    return render_template("semana.html")

@app.route("/timer")
def timer():
    return render_template("timer.html")

@app.route("/assistente")
def assistente():
    return render_template("assistente.html")

@app.route("/historico")
def historico():
    return render_template("historico.html")

@app.route("/perfil")
def perfil():
    return render_template("perfil.html")

@app.route("/imc")
def imc():
    return render_template("imc.html")

@app.route("/peso")
def peso():
    return render_template("peso.html")

@app.route("/api/exercicios/<grupo>/<objetivo>")
def get_exercicios(grupo, objetivo):
    if grupo in EXERCICIOS and objetivo in EXERCICIOS[grupo]["exercicios"]:
        return jsonify(EXERCICIOS[grupo]["exercicios"][objetivo])
    return jsonify([])

@app.route("/api/semana")
def get_semana():
    return jsonify(SEMANA_TREINO)

@app.route("/api/assistente", methods=["POST"])
def assistente_chat():
    data = request.json
    msg = data.get("mensagem", "").lower().strip()
    sessao_id = data.get("sessao_id", "")
    
    if not sessao_id or sessao_id not in sessoes:
        sessao_id = str(uuid.uuid4())[:8]
        sessoes[sessao_id] = {"objetivo": None, "ultimo_topico": None}
    
    sessao = sessoes[sessao_id]
    resposta = responder_ia(msg, sessao)
    
    return jsonify({"resposta": resposta, "sessao_id": sessao_id})

def responder_ia(msg, sessao):
    msg_lower = msg.lower().strip()

    palavras = msg_lower.split()
    saudacoes = ["oi", "ola", "bom dia", "boa tarde", "boa noite", "eai", "hello", "hi"]
    if any(w in palavras for w in saudacoes):
        sessao["ultimo_topico"] = None
        return "Ola! Sou o assistente do Zentrox Fit. Como posso te ajudar hoje?"

    if any(w in msg_lower for w in ["obrigado", "valeu", "thanks", "agradeco"]):
        return "De nada! Estou aqui para te ajudar. Bons treinos!"

    if any(w in msg_lower for w in ["perder peso", "emagrecer", "perder gordura", "perder barriga", "barriga", "barriguda", "barrigudo", "pancinha"]):
        sessao["objetivo"] = "perda_peso"
        return "Entendido! Seu objetivo e PERDER PESO. Vou te ajudar! 1) Treino com pouco descanso (30-45s) e repeticoes altas (15-20), 2) Cardio 3x por semana, 3) Deficit calorico de 300-500 calorias. Em 20 dias voce pode perder 2-4kg! Quer treinos de musculacao e dieta?"

    if any(w in msg_lower for w in ["20 dias", "20dias", "em 20 dias"]):
        sessao["objetivo"] = "perda_peso"
        return "Plano para 20 dias: 1) Treino 5x por semana, 2) Cardio 3x por semana (30min), 3) Dieta com deficit calorico, 4) Beber 3L de agua, 5) Dormir 7-8h. Resultado: 2-4kg a menos! Quer treinos e dieta?"

    if any(w in msg_lower for w in ["forca", "hipertrofia", "crescer", "massa muscular", "ficar forte", "ganhar massa"]):
        sessao["objetivo"] = "forca"
        return "Seu objetivo e GANHAR MASSA! 4-5 series de 8-12 reps com carga pesada. Descanse 90-120s. Progredir carga semanalmente! Quer treinos e dieta?"

    if any(w in msg_lower for w in ["definicao", "definir", "tonificar", "definido"]):
        sessao["objetivo"] = "definicao"
        return "Seu objetivo e DEFINICAO! 3-4 series de 12-15 reps com carga moderada. Descanse 45-60s. Cardio 3x por semana! Quer treinos e dieta?"

    if any(w in msg_lower for w in ["musculacao", "musculo", "treinar musculo", "musculos"]):
        if sessao.get("objetivo"):
            obj = sessao["objetivo"]
            nome_obj = {"perda_peso": "perda de peso", "forca": "forca/hipertrofia", "definicao": "definicao"}.get(obj, "fitness")
            return f"Para seu objetivo de {nome_obj}, divida: Segunda-Peito, Terca-Costas, Quarta-Pernas, Quinta-Ombros, Sexta-Bracos, Sabado-Pernas. Qual musculo voce quer treinar?"
        return "Musculacao e otima! Qual e seu objetivo? 1) Perder peso, 2) Ganhar massa/forca, 3) Definicao muscular"

    if any(w in msg_lower for w in ["peito", "supino", "treinar peito"]):
        sessao["ultimo_topico"] = "peito"
        if sessao.get("objetivo") == "perda_peso":
            return "TREINO DE PEITO (perda de peso): supino reto (4x12-15, descanso 45s), burpee com flexao (3x12), crossover (3x15-20, descanso 30s), flexao com palma (3x10-12). Faca cardio 3x por semana!"
        elif sessao.get("objetivo") == "forca":
            return "TREINO DE PEITO (forca): supino reto com barra (4x8-10, descanso 90s), supino inclinado (4x10-12), crucifixo (3x12-15), crossover (3x12-15). Descanse 75-90s!"
        elif sessao.get("objetivo") == "definicao":
            return "TREINO DE PEITO (definicao): supino reto com halteres (3x12-15, descanso 60s), crossover baixo (3x15-20), supino inclinado (3x12-15), crucifixo no chao (3x15-20). Descanse 45-60s!"
        return "Para peito: supino reto (4x8), supino inclinado (4x10), crucifixo (3x12), crossover (3x15). Descanse 75-90s!"

    if any(w in msg_lower for w in ["costas", "puxada", "treinar costas"]):
        sessao["ultimo_topico"] = "costas"
        if sessao.get("objetivo") == "perda_peso":
            return "TREINO DE COSTAS (perda de peso): remada curvada (4x12-15, descanso 45s), burpee (3x12-15), puxada frontal (3x15-20, descanso 30s), mountain climber (3x20-30). Cardio depois!"
        elif sessao.get("objetivo") == "forca":
            return "TREINO DE COSTAS (forca): puxada frontal (4x8-10, descanso 90s), remada curvada (4x8-10), remada unilateral (3x10-12), pulldown (3x12-15). Descanse 75-90s!"
        elif sessao.get("objetivo") == "definicao":
            return "TREINO DE COSTAS (definicao): puxada frontal (3x12-15, descanso 60s), remada curvada (3x12-15), pulldown (3x15-20), remada cavalinho (3x12-15). Descanse 45-60s!"
        return "Para costas: puxada frontal (4x8), remada curvada (4x10), remada unilateral (3x12), pulldown (3x15). Descanse 75-90s!"

    if any(w in msg_lower for w in ["pernas", "perna", "agachamento", "treinar pernas"]):
        sessao["ultimo_topico"] = "pernas"
        if sessao.get("objetivo") == "perda_peso":
            return "TREINO DE PERNAS (perda de peso): agachamento com salto (4x12-15, descanso 30s), leg press (4x15-20), avanco com halteres (3x15-20), agachamento isometrico (3x45-60s). Cardio!"
        elif sessao.get("objetivo") == "forca":
            return "TREINO DE PERNAS (forca): agachamento livre (4x8-10, descanso 120s), leg press 45° (4x10-12), cadeira extensora (3x12-15), mesa flexora (3x12-15). Descanse 90-120s!"
        elif sessao.get("objetivo") == "definicao":
            return "TREINO DE PERNAS (definicao): agachamento livre (3x12-15, descanso 60s), leg press (3x15-20), extensora (3x15-20), flexora (3x15-20). Descanse 45-60s!"
        return "Para pernas: agachamento (4x10), leg press (4x12), extensora (3x15), flexora (3x15), panturrilha (4x20). Descanse 90s!"

    if any(w in msg_lower for w in ["ombros", "ombro", "treinar ombros"]):
        sessao["ultimo_topico"] = "ombros"
        if sessao.get("objetivo") == "perda_peso":
            return "TREINO DE OMBROS (perda de peso): desenvolvimento (3x15-20, descanso 30s), elevacao lateral (3x20-25), burpee com elevacao (3x12-15), mountain climber (3x20-30). Cardio!"
        elif sessao.get("objetivo") == "forca":
            return "TREINO DE OMBROS (forca): desenvolvimento com barra (4x8-10, descanso 90s), elevacao lateral (3x10-12), face pull (3x12-15), arnold press (3x10-12). Descanse 75s!"
        elif sessao.get("objetivo") == "definicao":
            return "TREINO DE OMBROS (definicao): desenvolvimento (3x12-15, descanso 60s), elevacao lateral (3x15-20), face pull (3x15-20), arnold press (3x12-15). Descanse 45-60s!"
        return "Para ombros: desenvolvimento (4x10), elevacao lateral (3x12), face pull (3x15), arnold press (3x12). Descanse 60-75s!"

    if any(w in msg_lower for w in ["bracos", "braco", "biceps", "triceps", "treinar bracos"]):
        sessao["ultimo_topico"] = "bracos"
        if sessao.get("objetivo") == "perda_peso":
            return "TREINO DE BRACOS (perda de peso): rosca com burpee (3x12-15, descanso 30s), triceps no chao (3x15-20), rosca alternada (3x15-20), mergulho na cadeira (3x15-20). Cardio!"
        elif sessao.get("objetivo") == "forca":
            return "TREINO DE BRACOS (forca): rosca direta (4x8-10, descanso 75s), triceps testa (4x8-10), rosca alternada (3x10-12), triceps polia (3x10-12). Descanse 60-75s!"
        elif sessao.get("objetivo") == "definicao":
            return "TREINO DE BRACOS (definicao): rosca direta (3x12-15, descanso 60s), triceps testa (3x12-15), rosca alternada (3x15-20), triceps polia (3x15-20). Descanse 45-60s!"
        return "Para bracos: rosca direta (4x8), triceps testa (4x8), rosca alternada (3x12), triceps polia (3x12). Descanse 60-75s!"

    if any(w in msg_lower for w in ["abdomen", "treinar abdomen", "abdome"]):
        sessao["ultimo_topico"] = "abdomen"
        if sessao.get("objetivo") == "perda_peso":
            return "TREINO DE ABDOMEN (perda de peso): burpee (4x12-15, descanso 30s), mountain climber (3x20-30), prancha com toque (3x15-20), bicycle (3x20-30), high knees (3x30-40). Faca 4x por semana!"
        return "Para abdomen: prancha (3x60s), crunch (3x20), russian twist (3x20), elevacao de pernas (3x15), bicycle (3x20). Treine 3-4x por semana!"

    if any(w in msg_lower for w in ["dieta", "alimentacao", "comida", "o que comer", "como me alimentar"]):
        if sessao.get("objetivo") == "perda_peso":
            return "DIETA PARA PERDA DE PESO: Cafe: ovo + fruta. Lanche: fruta + oleaginosas. Almoco: arroz + frango + salada. Lanche: whey + fruta. Jantar: peixe + legumes. Beba 3L de agua! Deficit de 300-500 cal!"
        elif sessao.get("objetivo") == "forca":
            return "DIETA PARA GANHO DE MASSA: Cafe: aveia + ovo + banana. Lanche: whey + fruta. Almoco: arroz + carne + feijao + salada. Lanche: pasta de amendoim. Jantar: frango + batata doce. 1.6-2.2g proteina/kg!"
        elif sessao.get("objetivo") == "definicao":
            return "DIETA PARA DEFINICAO: Cafe: ovo + fruta. Lanche: fruta + castanhas. Almoco: arroz + peixe + legumes. Lanche: whey + fruta. Jantar: frango + salada. Deficit leve de 200-300 cal!"
        return "Dieta: cafe da manha com ovo e fruta, almoco com arroz/frango/salada, jantar leve com proteina. 1.6-2.2g de proteina/kg!"

    if any(w in msg_lower for w in ["cardio", "correr", "corrida", "bike", "bicicleta", "aerobico"]):
        if sessao.get("objetivo") == "perda_peso":
            return "CARDIO PARA PERDA DE PESO: 3x por semana, 30-45 minutos. Opcoes: corrida, bicicleta, eliptico, HIIT (20min). Faca cardio EM JEJUM ou apos musculacao!"
        return "Cardio: corrida (30min), bicicleta (40min), eliptico (30min), HIIT (20min). Faca 3x por semana!"

    if any(w in msg_lower for w in ["depois do treino", "apos treino", "pos treino", "recuperacao"]):
        if sessao.get("objetivo") == "perda_peso":
            return "APOS O TREINO (perda de peso): 1) 30min de cardio, 2) Beba agua, 3) Lanche com proteina (whey + fruta), 4) Durma 7-8h. Cardio depois do treino queima mais gordura!"
        elif sessao.get("objetivo") == "forca":
            return "APOS O TREINO (massa): 1) Lanche com proteina (whey + aveia), 2) Beba agua, 3) Durma 7-8h, 4) Prossiga com a dieta. Musculos crescem durante o descanso!"
        elif sessao.get("objetivo") == "definicao":
            return "APOS O TREINO (definicao): 1) Cardio leve (20min), 2) Lanche com proteina, 3) Beba agua, 4) Durma 7-8h. Mantenha a dieta!"
        return "Apos o treino: alongue 10min, beba agua, coma proteina (whey ou frango), durma 7-8h!"

    if any(w in msg_lower for w in ["exercicio em casa", "treino em casa", "casa", "sem academia"]):
        return "Em casa: flexao de bracos, agachamento, prancha, burpee, mountain climber, abdominal, elevacao de pernas. Sem equipamento!"

    if any(w in msg_lower for w in ["proteina"]):
        return "Boas fontes de proteina: peito de frango (31g/100g), ovo (6g/unidade), atum, carne magra, whey, feijao, lentilha. 1.6-2.2g por kg de peso!"

    if any(w in msg_lower for w in ["descanso", "dormir", "sono"]):
        return "Durma 7-9 horas por noite. Musculos crescem durante o repouso! Evite telas 1h antes de dormir."

    if any(w in msg_lower for w in ["agua", "hidratacao", "beber"]):
        return "Beba 2-3 litros de agua por dia. Durante o treino, beba a cada 15-20 minutos!"

    if any(w in msg_lower for w in ["aquecimento", "alongamento", "alongar"]):
        return "Faca 5-10 minutos de aquecimento antes: corrida leve, jumpin jack. Alongue ao final!"

    if any(w in msg_lower for w in ["timer", "cronometro", "descanso entre series"]):
        return "Use o timer! Para hipertrofia: 60-90s. Para forca: 90-120s. Para definicao: 30-45s!"

    if any(w in msg_lower for w in ["quanto tempo", "resultados"]):
        return "Resultados: 2 semanas para sentir, 4 semanas para ver, 8 semanas para outros notarem. Consistencia e a chave!"

    if any(w in msg_lower for w in ["personal", "professor"]):
        return "Sou seu assistente virtual! Consulte um educador fisico para orientacao personalizada!"

    if any(w in msg_lower for w in ["legal", "show", "massa", "top", "otimo", "blz"]):
        return "Valeu! Bons treinos!"

    if any(w in msg_lower for w in ["tudo bem", "como vai"]):
        return "Tudo bem! Pronto pra te ajudar!"

    if any(w in msg_lower for w in ["me ajude", "ajuda", "help"]):
        return "Posso te ajudar com: treinos, dieta, cardio, descanso, hidratacao. O que voce quer saber?"

    if any(w in msg_lower for w in ["quero", "gostaria", "pretendo", "vou"]):
        if sessao.get("objetivo"):
            obj = sessao["objetivo"]
            nome_obj = {"perda_peso": "perda de peso", "forca": "forca/hipertrofia", "definicao": "definicao"}.get(obj, "fitness")
            return f"Seu objetivo e {nome_obj}! Me diga: treinos de qual musculo? Ou dieta, cardio, descanso?"
        return "Qual e seu objetivo? 1) Perder peso, 2) Ganhar massa/forca, 3) Definicao muscular"

    if any(w in msg_lower for w in ["ok", "beleza", "entendi", "certo", "sim", "isso"]):
        if sessao.get("ultimo_topico"):
            return f"Quer treinos de outro musculo? Ou dicas de dieta, cardio, recuperacao?"
        return "Certo! Me conta o que voce precisa!"

    if any(w in msg_lower for w in ["treinos", "treino", "exercicios"]):
        if sessao.get("objetivo"):
            obj = sessao["objetivo"]
            nome_obj = {"perda_peso": "perda de peso", "forca": "forca/hipertrofia", "definicao": "definicao"}.get(obj, "fitness")
            return f"Para {nome_obj}, treine: Peito, Costas, Pernas, Ombros, Bracos, Abdomen. Qual musculo?"
        return "Qual musculo? 1) Peito, 2) Costas, 3) Pernas, 4) Ombros, 5) Bracos, 6) Abdomen"

    return "Me conte mais: seu objetivo e perder peso, ganhar massa ou definir? Assim posso te ajudar melhor!"

if __name__ == "__main__":
    app.run(debug=True, port=5001)
