"""
Power Fit - App de Treinos para Smartphone
Framework: Kivy
"""

import os
import sys
from datetime import datetime
from pathlib import Path

from kivy.utils import rgba as kv_rgba
from kivy.metrics import dp, sp
from kivy.app import App
from kivy.lang import Builder
from kivy.uix.screenmanager import ScreenManager, Screen, FadeTransition
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.scrollview import ScrollView
from kivy.uix.gridlayout import GridLayout
from kivy.uix.label import Label
from kivy.uix.button import Button
from kivy.uix.textinput import TextInput
from kivy.uix.spinner import Spinner
from kivy.clock import Clock
from kivy.core.window import Window

# ── Configuracao ──────────────────────────────
if getattr(sys, 'android', None):
    ANDROID = True
    from android.permissions import request_permissions, Permission
    request_permissions([Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE])
    DATA_DIR = os.path.join(os.path.expanduser("~"), ".powerfit")
else:
    ANDROID = False
    DATA_DIR = os.path.join(os.path.dirname(os.path.abspath(__file__)), "data")

os.makedirs(DATA_DIR, exist_ok=True)
ARQUIVO_USUARIO = os.path.join(DATA_DIR, "usuario.json")

# ── Exercicios ────────────────────────────────
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
    "forca": "Forca",
    "definicao": "Definicao",
    "perda_de_peso": "Perda de peso",
}

CORES = {
    "bg": "#121212",
    "card": "#1e1e1e",
    "card2": "#2a2a2a",
    "texto": "#e0e0e0",
    "titulo": "#ff4757",
    "btn": "#2d2d2d",
    "btn_pressed": "#3d3d3d",
    "accent": "#4fc3f7",
    "verde": "#66bb6a",
    "amarelo": "#ffd54f",
}

# ── KV Language ────────────────────────────────
KV = f'''
#:import rgba kivy.utils.rgba
#:import FadeTransition kivy.uix.screenmanager.FadeTransition

<RoundedCard@BoxLayout>:
    orientation: "vertical"
    padding: dp(16)
    spacing: dp(8)
    size_hint_y: None
    height: self.minimum_height
    canvas.before:
        Color:
            rgba: rgba("{CORES['card']}ff")
        RoundedRectangle:
            pos: self.pos
            size: self.size
            radius: [dp(12)]

ScreenManager:
    id: sm
    transition: FadeTransition(duration=0.15)
'''


# ── Widgets Python (usados nas telas) ──────────
class DarkInput(TextInput):
    def __init__(self, **kw):
        super().__init__(
            background_color=kv_rgba(f"{CORES['card2']}ff"),
            foreground_color=kv_rgba(f"{CORES['texto']}ff"),
            cursor_color=kv_rgba(f"{CORES['accent']}ff"),
            font_size=sp(16),
            size_hint_y=None,
            height=dp(50),
            padding=[dp(12), dp(12)],
            multiline=False,
            **kw,
        )


class RoundedBtn(Button):
    def __init__(self, **kw):
        super().__init__(
            background_normal="",
            background_color=kv_rgba(f"{CORES['btn']}ff"),
            color=kv_rgba(f"{CORES['texto']}ff"),
            font_size=sp(16),
            size_hint_y=None,
            height=dp(50),
            **kw,
        )


class SubLabel(Label):
    def __init__(self, **kw):
        super().__init__(
            font_size=sp(14),
            color=kv_rgba(f"{CORES['texto']}ff"),
            size_hint_y=None,
            height=dp(30),
            **kw,
        )


# ── Persistencia ───────────────────────────────
def _defaults():
    return {
        "nome": "",
        "peso": 0.0,
        "altura": 0.0,
        "objetivo": "forca",
        "treinos_feitos": [],
        "historico_peso": [],
    }

def carregar():
    if os.path.exists(ARQUIVO_USUARIO):
        try:
            import json
            with open(ARQUIVO_USUARIO, "r", encoding="utf-8") as f:
                dados = json.load(f)
                dados.setdefault("peso", 0.0)
                dados.setdefault("altura", 0.0)
                dados.setdefault("treinos_feitos", [])
                dados.setdefault("historico_peso", [])
                return dados
        except (Exception, IOError):
            return _defaults()
    return _defaults()

def salvar(dados):
    try:
        import json
        with open(ARQUIVO_USUARIO, "w", encoding="utf-8") as f:
            json.dump(dados, f, indent=2, ensure_ascii=False)
    except IOError:
        pass

def calcular_imc(peso, altura):
    if altura <= 0:
        return 0.0, "Altura invalida"
    imc = peso / (altura * altura)
    if imc < 18.5:
        cls = "Abaixo do peso"
    elif imc < 25:
        cls = "Peso normal"
    elif imc < 30:
        cls = "Sobrepeso"
    else:
        cls = "Obesidade"
    return imc, cls

def hoje_str():
    return datetime.now().strftime("%d/%m/%Y")

def dia_da_semana():
    mapa = {0: "segunda", 1: "terca", 2: "quarta",
            3: "quinta", 4: "sexta", 5: "sabado", 6: "domingo"}
    return mapa[datetime.now().weekday()]


# ═══════════════════════════════════════════════
#  TELAS
# ═══════════════════════════════════════════════

class TelaCadastro(Screen):
    def __init__(self, **kw):
        super().__init__(**kw)
        layout = BoxLayout(orientation="vertical", padding=dp(24), spacing=dp(12))

        layout.add_widget(Label(
            text="POWER FIT",
            font_size=sp(32), bold=True,
            color=kv_rgba(f"{CORES['titulo']}ff"),
            size_hint_y=None, height=dp(50)))

        layout.add_widget(Label(
            text="Cadastre seu perfil",
            font_size=sp(16),
            color=kv_rgba(f"{CORES['texto']}88"),
            size_hint_y=None, height=dp(30)))

        self.input_nome = DarkInput(hint_text="Seu nome")
        layout.add_widget(self.input_nome)

        self.input_peso = DarkInput(hint_text="Peso (kg)", input_filter="float")
        layout.add_widget(self.input_peso)

        self.input_altura = DarkInput(hint_text="Altura (m) ex: 1.65", input_filter="float")
        layout.add_widget(self.input_altura)

        layout.add_widget(SubLabel(text="Objetivo:"))
        self.spinner_obj = Spinner(
            text="Forca",
            values=["Forca", "Definicao", "Perda de peso"],
            size_hint_y=None, height=dp(50),
            background_normal="",
            background_color=kv_rgba(f"{CORES['card2']}ff"),
            color=kv_rgba(f"{CORES['texto']}ff"),
        )
        layout.add_widget(self.spinner_obj)

        btn = RoundedBtn(text="COMEÇAR", bold=True)
        btn.bind(on_press=self.salvar_perfil)
        layout.add_widget(btn)

        scroll = ScrollView()
        scroll.add_widget(layout)
        self.add_widget(scroll)

    def salvar_perfil(self, *a):
        nome = self.input_nome.text.strip()
        if not nome:
            return
        try:
            peso = float(self.input_peso.text.replace(",", "."))
            altura = float(self.input_altura.text.replace(",", "."))
        except ValueError:
            return

        mapa = {"Forca": "forca", "Definicao": "definicao", "Perda de peso": "perda_de_peso"}
        dados = carregar()
        dados["nome"] = nome
        dados["peso"] = peso
        dados["altura"] = altura
        dados["objetivo"] = mapa.get(self.spinner_obj.text, "forca")
        salvar(dados)
        self.manager.current = "menu"


class TelaMenu(Screen):
    def __init__(self, **kw):
        super().__init__(**kw)
        self.layout = BoxLayout(orientation="vertical", padding=dp(16), spacing=dp(10))
        self.scroll = ScrollView()
        self.scroll.add_widget(self.layout)
        self.add_widget(self.scroll)

    def on_enter(self):
        self.layout.clear_widgets()
        dados = carregar()
        nome = dados.get("nome", "Atleta")
        peso = dados.get("peso", 0)
        altura = dados.get("altura", 0)
        obj = OBJETIVOS_LABEL.get(dados.get("objetivo", ""), "")
        imc, cls_imc = calcular_imc(peso, altura)

        header = BoxLayout(orientation="vertical", size_hint_y=None, height=dp(100), spacing=dp(4))
        header.add_widget(Label(
            text=f"Ola, {nome}!",
            font_size=sp(20), bold=True,
            color=kv_rgba(f"{CORES['texto']}ff"),
            size_hint_y=None, height=dp(30),
            halign="left"))
        header.add_widget(Label(
            text=f"Peso: {peso}kg | IMC: {imc:.1f} ({cls_imc}) | {obj}",
            font_size=sp(12),
            color=kv_rgba(f"{CORES['texto']}88"),
            size_hint_y=None, height=dp(20),
            halign="left"))
        self.layout.add_widget(header)

        itens = [
            ("Treino do dia", "treino_dia"),
            ("Treino da semana", "treino_semana"),
            ("Treinar com timer", "timer"),
            ("Historico de treinos", "historico"),
            ("Registrar peso", "reg_peso"),
            ("Evolucao do peso", "evolucao"),
            ("Ver IMC", "ver_imc"),
            ("Atualizar perfil", "cadastro"),
        ]
        for texto, tela in itens:
            btn = RoundedBtn(text=texto, halign="left", padding=[dp(16), 0])
            btn.bind(on_press=lambda s, t=tela: self.ir(t))
            self.layout.add_widget(btn)

    def ir(self, tela):
        self.manager.current = tela


class TelaTreinoDia(Screen):
    def __init__(self, **kw):
        super().__init__(**kw)
        self.scroll = ScrollView()
        self.box = BoxLayout(orientation="vertical", padding=dp(16), spacing=dp(10),
                             size_hint_y=None)
        self.box.bind(minimum_height=self.box.setter("height"))
        self.scroll.add_widget(self.box)
        self.add_widget(self.scroll)

    def on_enter(self):
        self.box.clear_widgets()
        dados = carregar()
        objetivo = dados.get("objetivo", "forca")
        dia = dia_da_semana()
        musculos = SPLIT_SEMANAL.get(dia, [])

        self.box.add_widget(Label(
            text=f"TREINO - {dia.upper()}",
            font_size=sp(20), bold=True,
            color=kv_rgba(f"{CORES['titulo']}ff"),
            size_hint_y=None, height=dp(40)))

        if not musculos:
            self.box.add_widget(Label(
                text="Hoje e dia de descanso!",
                font_size=sp(16), color=kv_rgba(f"{CORES['texto']}88"),
                size_hint_y=None, height=dp(40)))
            return

        for musculo in musculos:
            card = BoxLayout(orientation="vertical", padding=dp(12), spacing=dp(6),
                             size_hint_y=None)
            card.bind(minimum_height=card.setter("height"))
            card.add_widget(Label(
                text=musculo.upper(),
                font_size=sp(16), bold=True,
                color=kv_rgba(f"{CORES['accent']}ff"),
                size_hint_y=None, height=dp(30)))

            lista = EXERCICIOS.get(musculo, {}).get(objetivo, [])
            for nome, series, reps, desc in lista:
                lbl = Label(
                    text=f"{nome}\n{series}x {reps} | Desc: {desc}s",
                    font_size=sp(13),
                    color=kv_rgba(f"{CORES['texto']}cc"),
                    size_hint_y=None, height=dp(45),
                    halign="left", valign="top",
                    text_size=(Window.width - dp(60), None))
                card.add_widget(lbl)

            self.box.add_widget(card)


class TelaTreinoSemana(Screen):
    def __init__(self, **kw):
        super().__init__(**kw)
        self.scroll = ScrollView()
        self.box = BoxLayout(orientation="vertical", padding=dp(16), spacing=dp(10),
                             size_hint_y=None)
        self.box.bind(minimum_height=self.box.setter("height"))
        self.scroll.add_widget(self.box)
        self.add_widget(self.scroll)

    def on_enter(self):
        self.box.clear_widgets()
        dados = carregar()
        objetivo = dados.get("objetivo", "forca")

        self.box.add_widget(Label(
            text="TREINO DA SEMANA",
            font_size=sp(20), bold=True,
            color=kv_rgba(f"{CORES['titulo']}ff"),
            size_hint_y=None, height=dp(40)))

        for dia in DIAS_SEMANA:
            musculos = SPLIT_SEMANAL.get(dia, [])
            card = BoxLayout(orientation="vertical", padding=dp(12), spacing=dp(6),
                             size_hint_y=None)
            card.bind(minimum_height=card.setter("height"))
            card.add_widget(Label(
                text=dia.upper(),
                font_size=sp(16), bold=True,
                color=kv_rgba(f"{CORES['accent']}ff"),
                size_hint_y=None, height=dp(30)))

            for musculo in musculos:
                lista = EXERCICIOS.get(musculo, {}).get(objetivo, [])
                textos = [f"  {n} ({s}x {r})" for n, s, r, _ in lista]
                card.add_widget(Label(
                    text=f"[b]{musculo.upper()}[/b]\n" + "\n".join(textos),
                    font_size=sp(12),
                    color=kv_rgba(f"{CORES['texto']}cc"),
                    size_hint_y=None, height=dp(20 + len(textos) * 18),
                    halign="left", markup=True,
                    text_size=(Window.width - dp(50), None)))

            self.box.add_widget(card)


class TelaTimer(Screen):
    def __init__(self, **kw):
        super().__init__(**kw)
        self.timer_event = None
        self.tempo = 0
        self.pausado = True

        layout = BoxLayout(orientation="vertical", padding=dp(24), spacing=dp(16))

        self.lbl_titulo = Label(
            text="TIMER",
            font_size=sp(22), bold=True,
            color=kv_rgba(f"{CORES['titulo']}ff"),
            size_hint_y=None, height=dp(40))

        self.lbl_tempo = Label(
            text="00:00",
            font_size=sp(64), bold=True,
            color=kv_rgba(f"{CORES['accent']}ff"),
            size_hint_y=None, height=dp(100))

        layout.add_widget(self.lbl_titulo)
        layout.add_widget(self.lbl_tempo)

        btns = BoxLayout(spacing=dp(10), size_hint_y=None, height=dp(50))
        self.btn_start = RoundedBtn(text="INICIAR")
        self.btn_start.bind(on_press=self.toggle)
        self.btn_reset = RoundedBtn(text="RESETAR")
        self.btn_reset.bind(on_press=self.resetar)
        btns.add_widget(self.btn_start)
        btns.add_widget(self.btn_reset)
        layout.add_widget(btns)

        layout.add_widget(Label(
            text="Toque para pausar/continuar",
            font_size=sp(12),
            color=kv_rgba(f"{CORES['texto']}55"),
            size_hint_y=None, height=dp(20)))

        layout.add_widget(BoxLayout(size_hint_y=1))
        self.add_widget(layout)

    def toggle(self, *a):
        if self.pausado:
            self.pausado = False
            self.btn_start.text = "PAUSAR"
            self.timer_event = Clock.schedule_interval(self.atualizar, 1)
        else:
            self.pausado = True
            self.btn_start.text = "CONTINUAR"
            if self.timer_event:
                self.timer_event.cancel()

    def atualizar(self, dt):
        if not self.pausado:
            self.tempo += 1
            m, s = divmod(self.tempo, 60)
            self.lbl_tempo.text = f"{m:02d}:{s:02d}"

    def resetar(self, *a):
        self.pausado = True
        self.tempo = 0
        self.lbl_tempo.text = "00:00"
        self.btn_start.text = "INICIAR"
        if self.timer_event:
            self.timer_event.cancel()

    def on_leave(self):
        if self.timer_event:
            self.timer_event.cancel()
        self.pausado = True
        self.tempo = 0
        self.lbl_tempo.text = "00:00"
        self.btn_start.text = "INICIAR"


class TelaHistorico(Screen):
    def __init__(self, **kw):
        super().__init__(**kw)
        self.scroll = ScrollView()
        self.box = BoxLayout(orientation="vertical", padding=dp(16), spacing=dp(8),
                             size_hint_y=None)
        self.box.bind(minimum_height=self.box.setter("height"))
        self.scroll.add_widget(self.box)
        self.add_widget(self.scroll)

    def on_enter(self):
        self.box.clear_widgets()
        dados = carregar()
        treinos = dados.get("treinos_feitos", [])

        self.box.add_widget(Label(
            text="HISTORICO",
            font_size=sp(20), bold=True,
            color=kv_rgba(f"{CORES['titulo']}ff"),
            size_hint_y=None, height=dp(40)))

        if not treinos:
            self.box.add_widget(Label(
                text="Nenhum treino registrado.",
                font_size=sp(14),
                color=kv_rgba(f"{CORES['texto']}55"),
                size_hint_y=None, height=dp(40)))
            return

        for t in reversed(treinos[-30:]):
            card = BoxLayout(orientation="vertical", padding=dp(12), spacing=dp(4),
                             size_hint_y=None, height=dp(60))
            card.add_widget(Label(
                text=t.get("data", ""),
                font_size=sp(14), bold=True,
                color=kv_rgba(f"{CORES['accent']}ff"),
                size_hint_y=None, height=dp(22),
                halign="left", text_size=(Window.width - dp(50), None)))
            card.add_widget(Label(
                text=t.get("musculos", ""),
                font_size=sp(12),
                color=kv_rgba(f"{CORES['texto']}cc"),
                size_hint_y=None, height=dp(20),
                halign="left", text_size=(Window.width - dp(50), None)))
            self.box.add_widget(card)


class TelaRegistrarPeso(Screen):
    def __init__(self, **kw):
        super().__init__(**kw)
        layout = BoxLayout(orientation="vertical", padding=dp(24), spacing=dp(12))

        layout.add_widget(Label(
            text="REGISTRAR PESO",
            font_size=sp(20), bold=True,
            color=kv_rgba(f"{CORES['titulo']}ff"),
            size_hint_y=None, height=dp(40)))

        self.input_peso = DarkInput(hint_text="Peso atual (kg)", input_filter="float")
        layout.add_widget(self.input_peso)

        btn = RoundedBtn(text="SALVAR")
        btn.bind(on_press=self.salvar_peso)
        layout.add_widget(btn)

        self.lbl_status = Label(
            text="", font_size=sp(14),
            color=kv_rgba(f"{CORES['verde']}ff"),
            size_hint_y=None, height=dp(30))
        layout.add_widget(self.lbl_status)
        layout.add_widget(BoxLayout(size_hint_y=1))
        self.add_widget(layout)

    def on_enter(self):
        self.input_peso.text = ""
        self.lbl_status.text = ""

    def salvar_peso(self, *a):
        try:
            peso = float(self.input_peso.text.replace(",", "."))
        except ValueError:
            return
        dados = carregar()
        registro = {"peso": peso, "data": hoje_str()}
        dados["historico_peso"].append(registro)
        dados["peso"] = peso
        salvar(dados)
        self.lbl_status.text = f"Peso {peso}kg salvo!"


class TelaEvolucaoPeso(Screen):
    def __init__(self, **kw):
        super().__init__(**kw)
        self.scroll = ScrollView()
        self.box = BoxLayout(orientation="vertical", padding=dp(16), spacing=dp(8),
                             size_hint_y=None)
        self.box.bind(minimum_height=self.box.setter("height"))
        self.scroll.add_widget(self.box)
        self.add_widget(self.scroll)

    def on_enter(self):
        self.box.clear_widgets()
        dados = carregar()
        historico = dados.get("historico_peso", [])

        self.box.add_widget(Label(
            text="EVOLUCAO DO PESO",
            font_size=sp(20), bold=True,
            color=kv_rgba(f"{CORES['titulo']}ff"),
            size_hint_y=None, height=dp(40)))

        if not historico:
            self.box.add_widget(Label(
                text="Nenhum registro de peso.",
                font_size=sp(14),
                color=kv_rgba(f"{CORES['texto']}55"),
                size_hint_y=None, height=dp(40)))
            return

        primeiro = historico[0]["peso"]
        for reg in reversed(historico[-20:]):
            p = reg["peso"]
            diff = p - primeiro
            sinal = "+" if diff > 0 else ""
            cor = CORES["verde"] if diff <= 0 else CORES["amarelo"]
            card = BoxLayout(orientation="horizontal", padding=dp(12),
                             size_hint_y=None, height=dp(50))
            card.add_widget(Label(
                text=reg.get("data", ""),
                font_size=sp(14),
                color=kv_rgba(f"{CORES['texto']}cc"),
                size_hint_x=0.4, halign="left"))
            card.add_widget(Label(
                text=f"{p}kg",
                font_size=sp(14), bold=True,
                color=kv_rgba(f"{CORES['texto']}ff"),
                size_hint_x=0.3))
            card.add_widget(Label(
                text=f"{sinal}{diff:.1f}kg",
                font_size=sp(12),
                color=kv_rgba(f"{cor}ff"),
                size_hint_x=0.3))
            self.box.add_widget(card)


class TelaIMC(Screen):
    def __init__(self, **kw):
        super().__init__(**kw)
        self.scroll = ScrollView()
        self.box = BoxLayout(orientation="vertical", padding=dp(16), spacing=dp(10),
                             size_hint_y=None)
        self.box.bind(minimum_height=self.box.setter("height"))
        self.scroll.add_widget(self.box)
        self.add_widget(self.scroll)

    def on_enter(self):
        self.box.clear_widgets()
        dados = carregar()
        peso = dados.get("peso", 0)
        altura = dados.get("altura", 0)
        imc, cls = calcular_imc(peso, altura)

        self.box.add_widget(Label(
            text="SEU IMC",
            font_size=sp(20), bold=True,
            color=kv_rgba(f"{CORES['titulo']}ff"),
            size_hint_y=None, height=dp(40)))

        self.box.add_widget(Label(
            text=f"{imc:.1f}",
            font_size=sp(48), bold=True,
            color=kv_rgba(f"{CORES['accent']}ff"),
            size_hint_y=None, height=dp(70)))

        self.box.add_widget(Label(
            text=cls,
            font_size=sp(18),
            color=kv_rgba(f"{CORES['texto']}cc"),
            size_hint_y=None, height=dp(30)))

        info = (
            f"Peso: {peso}kg\n"
            f"Altura: {altura}m\n\n"
            "Referencia:\n"
            "Abaixo de 18.5 - Abaixo do peso\n"
            "18.5 a 24.9 - Peso normal\n"
            "25.0 a 29.9 - Sobrepeso\n"
            "30.0 ou mais - Obesidade"
        )
        self.box.add_widget(Label(
            text=info,
            font_size=sp(13),
            color=kv_rgba(f"{CORES['texto']}99"),
            size_hint_y=None, height=dp(160),
            halign="left", valign="top",
            text_size=(Window.width - dp(50), None)))


# ═══════════════════════════════════════════════
#  APP
# ═══════════════════════════════════════════════

class PowerFitApp(App):
    def build(self):
        Window.clearcolor = kv_rgba(f"{CORES['bg']}ff")
        Builder.load_string(KV)

        sm = ScreenManager()
        sm.add_widget(TelaCadastro(name="cadastro"))
        sm.add_widget(TelaMenu(name="menu"))
        sm.add_widget(TelaTreinoDia(name="treino_dia"))
        sm.add_widget(TelaTreinoSemana(name="treino_semana"))
        sm.add_widget(TelaTimer(name="timer"))
        sm.add_widget(TelaHistorico(name="historico"))
        sm.add_widget(TelaRegistrarPeso(name="reg_peso"))
        sm.add_widget(TelaEvolucaoPeso(name="evolucao"))
        sm.add_widget(TelaIMC(name="ver_imc"))

        dados = carregar()
        if dados.get("nome"):
            sm.current = "menu"
        else:
            sm.current = "cadastro"

        return sm


if __name__ == "__main__":
    PowerFitApp().run()
