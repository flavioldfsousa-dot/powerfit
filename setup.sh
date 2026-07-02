#!/bin/bash
# Power Fit - Script de configuracao e build
# Execute: bash setup.sh

set -e

echo "========================================="
echo "  POWER FIT - Configuracao Completa"
echo "========================================="

# ── 1. Configurar git ──
echo ""
echo "[1/5] Configurando git..."
git config --global user.name "AnnaGabrielly"
git config --global user.email "annagabrielly@users.noreply.github.com"
echo "  OK"

# ── 2. Instalar dependencias ──
echo ""
echo "[2/5] Instalando dependencias..."
pip3 install customtkinter kivy pygame-ce Pillow
echo "  OK"

# ── 3. Configurar Python 3.13 (para Kivy) ──
echo ""
echo "[3/5] Verificando Python 3.13..."
if [ -d "$HOME/.pyenv/versions/3.13.14" ]; then
    echo "  Python 3.13 ja instalado"
else
    echo "  Instalando Python 3.13 via pyenv..."
    pyenv install 3.13.14
    ~/.pyenv/versions/3.13.14/bin/pip3 install kivy pygame-ce Pillow
fi
echo "  OK"

# ── 4. GitHub ──
echo ""
echo "[4/5] Configurando GitHub..."
if command -v gh &> /dev/null; then
    echo "  gh ja instalado"
else
    brew install gh
fi
echo "  Repositorio: https://github.com/flavioldfsousa-dot/powerfit"
echo "  OK"

# ── 5. Resumo ──
echo ""
echo "[5/5] Resumo:"
echo ""
echo "  Repositorio: https://github.com/flavioldfsousa-dot/powerfit"
echo "  APK:         https://github.com/flavioldfsousa-dot/powerfit/actions"
echo ""
echo "  Como usar:"
echo "    CLI:       python3 app.py"
echo "    Desktop:   python3 gui.py"
echo "    Mobile:    ~/.pyenv/versions/3.13.14/bin/python3 main.py"
echo "    APK:       Baixe em Actions > powerfit-apk"
echo ""
echo "========================================="
echo "  CONFIGURACAO COMPLETA!"
echo "========================================="
