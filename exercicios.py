"""
exercicios.py - Banco de dados de exercicios do Power Fit.

Modulo responsavel por armazenar todos os exercicios organizados
por grupo muscular e objetivo (forca, definicao, perda de peso).
"""

# ──────────────────────────────────────────────
# EXERCICIOS POR GRUPO MUSCULAR E OBJETIVO
# Estrutura: exercicios[musculo][objetivo] = [(nome, series, reps, descanso)]
# ──────────────────────────────────────────────

EXERCICIOS = {
    "peito": {
        "forca": [
            ("Supino Reto com Barra", 4, "8-10", 90),
            ("Supino Inclinado com Halteres", 4, "8-10", 90),
            ("Crucifixo na Maquina", 3, "10-12", 60),
        ],
        "definicao": [
            ("Supino Reto com Halteres", 4, "12-15", 45),
            ("Crossover", 4, "15-20", 30),
            ("Flexao de Bracos", 3, "ate falha", 30),
        ],
        "perda_de_peso": [
            ("Supino com Halteres (circuito)", 3, "15-20", 20),
            ("Flexao de Bracos", 3, "15-20", 20),
            ("Crossover Alto", 3, "20", 20),
        ],
    },
    "costas": {
        "forca": [
            ("Barra Fixa (Pull-up)", 4, "6-10", 90),
            ("Remada Curvada com Barra", 4, "8-10", 90),
            ("Pulldown na Maquina", 3, "10-12", 60),
        ],
        "definicao": [
            ("Remada Unilateral com Halter", 4, "12-15", 45),
            ("Pulldown Frontal", 4, "15-20", 30),
            ("Pullover na Maquina", 3, "15-20", 30),
        ],
        "perda_de_peso": [
            ("Remada com Halter (circuito)", 3, "15-20", 20),
            ("Pulldown", 3, "20", 20),
            ("Prancha com Remada", 3, "12 cada lado", 20),
        ],
    },
    "bracos": {
        "forca": [
            ("Rosca Direta com Barra", 4, "8-10", 60),
            ("Rosca Alternada com Halteres", 4, "8-10", 60),
            ("Rosca Martelo", 3, "10-12", 60),
        ],
        "definicao": [
            ("Rosca Direta com Barra", 4, "12-15", 30),
            ("Rosca Concentrada", 3, "15-20", 30),
            ("Rosca no Cabo", 3, "15-20", 30),
        ],
        "perda_de_peso": [
            ("Rosca com Halteres (circuito)", 3, "20", 15),
            ("Rosca Martelo", 3, "20", 15),
            ("Rosca Spider", 3, "15-20", 15),
        ],
    },
    "pernas": {
        "forca": [
            ("Agachamento Livre", 4, "6-10", 120),
            ("Leg Press 45", 4, "8-10", 90),
            ("Stiff", 4, "8-10", 90),
            ("Cadeira Extensora", 3, "10-12", 60),
        ],
        "definicao": [
            ("Agachamento com Halteres", 4, "12-15", 45),
            ("Leg Press", 4, "15-20", 45),
            ("Cadeira Flexora", 4, "15-20", 30),
            ("Panturrilha em Pe", 4, "20-25", 30),
        ],
        "perda_de_peso": [
            ("Agachamento (circuito)", 3, "20", 20),
            ("Afundo com Halteres", 3, "15 cada perna", 20),
            ("Leg Press", 3, "20", 20),
            ("Panturrilha", 3, "25", 20),
        ],
    },
    "ombro": {
        "forca": [
            ("Desenvolvimento com Barra", 4, "8-10", 90),
            ("Elevacao Lateral com Halteres", 4, "10-12", 60),
            ("Face Pull", 3, "12-15", 60),
        ],
        "definicao": [
            ("Desenvolvimento com Halteres", 4, "12-15", 45),
            ("Elevacao Lateral", 4, "15-20", 30),
            ("Elevacao Frontal", 3, "15-20", 30),
        ],
        "perda_de_peso": [
            ("Desenvolvimento (circuito)", 3, "15-20", 20),
            ("Elevacao Lateral", 3, "20", 20),
            ("Arnold Press", 3, "15-20", 20),
        ],
    },
    "abdomen": {
        "forca": [
            ("Prancha", 4, "45-60s", 30),
            ("Elevacao de Pernas", 4, "12-15", 45),
            ("Russian Twist com Halter", 3, "15 cada lado", 30),
        ],
        "definicao": [
            ("Crunch na Maquina", 4, "20", 30),
            ("Elevacao de Pernas", 4, "20", 30),
            ("Prancha Lateral", 3, "30-40s cada lado", 20),
        ],
        "perda_de_peso": [
            ("Bicycle Crunch", 3, "20 cada lado", 15),
            ("Mountain Climber", 3, "30s", 15),
            ("Prancha", 3, "45s", 15),
        ],
    },
}

# ──────────────────────────────────────────────
# SPLIT SEMANAL (2 musculos/dia, pernas 3x)
# ──────────────────────────────────────────────

SPLIT_SEMANAL = {
    "segunda": ["peito", "bracos"],
    "terca": ["costas", "bracos"],
    "quarta": ["pernas", "abdomen"],
    "quinta": ["ombro", "peito"],
    "sexta": ["pernas", "costas"],
    "sabado": ["pernas", "bracos"],
}

DIAS_SEMANA = ["segunda", "terca", "quarta", "quinta", "sexta", "sabado"]

OBJETIVOS = {
    "1": "forca",
    "2": "definicao",
    "3": "perda_de_peso",
}

OBJETIVOS_LABEL = {
    "forca": "Forca (hipertrofia)",
    "definicao": "Definicao muscular",
    "perda_de_peso": "Perda de peso",
}
