"""
config.py - Configuracoes do sistema Power Fit.
"""

import os

# ──────────────────────────────────────────────
# CAMINHOS (relativos ao diretorio do projeto)
# ──────────────────────────────────────────────

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
DATA_DIR = os.path.join(BASE_DIR, "data")

# Garante que a pasta data existe
os.makedirs(DATA_DIR, exist_ok=True)

ARQUIVO_USUARIO = os.path.join(DATA_DIR, "usuario.json")

# ──────────────────────────────────────────────
# CONFIGURACOES DA GUI
# ──────────────────────────────────────────────

APP_TITULO = "POWER FIT"
APP_LARGURA = 520
APP_ALTURA = 700

# Cores
COR_BG = "#0d1117"
COR_CARD = "#161b22"
COR_TEXTO = "#c9d1d9"
COR_TITULO = "#ff4757"
COR_BTN = "#21262d"
COR_BTN_HOVER = "#30363d"
COR_DESTAQUE = "#58a6ff"

# ──────────────────────────────────────────────
# CONFIGURACOES DO CLI
# ──────────────────────────────────────────────

CLI_LARGURA_LINHA = 50
