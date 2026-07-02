"""
app.py - Aplicacao CLI do Power Fit.

Modulo principal para uso via terminal.
Execute: python3 app.py
"""

import os
import sys
import time
from datetime import datetime

# Adiciona o diretorio do projeto ao path
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

from config import CLI_LARGURA_LINHA
from exercicios import EXERCICIOS, SPLIT_SEMANAL, DIAS_SEMANA, OBJETIVOS, OBJETIVOS_LABEL
from database import carregar, salvar, calcular_imc


def limpar_tela():
    """Limpa a tela do terminal."""
    os.system("cls" if os.name == "nt" else "clear")


def linha():
    """Imprime uma linha separadora."""
    print("=" * CLI_LARGURA_LINHA)


def pausa():
    """Pausa ate o usuario pressionar Enter."""
    input("\nPressione Enter para continuar...")


# ──────────────────────────────────────────────
# CADASTRO E PERFIL
# ──────────────────────────────────────────────

def cadastrar_usuario(dados):
    """
    Cadastra um novo usuario ou atualiza o perfil.

    Args:
        dados (dict): Dados do usuario.
    """
    limpar_tela()
    linha()
    print("  CADASTRAR PERFIL")
    linha()

    dados["nome"] = input("Seu nome: ").strip()

    while True:
        try:
            dados["peso"] = float(input("Seu peso (kg): ").strip())
            if dados["peso"] > 0:
                break
            print("  Peso deve ser maior que 0.")
        except ValueError:
            print("  Digite um numero valido.")

    while True:
        try:
            dados["altura"] = float(input("Sua altura (m) ex: 1.65: ").strip())
            if dados["altura"] > 0:
                break
            print("  Altura deve ser maior que 0.")
        except ValueError:
            print("  Digite um numero valido.")

    print("\nObjetivo:")
    for chave, label in OBJETIVOS_LABEL.items():
        num = [k for k, v in OBJETIVOS.items() if v == chave][0]
        print(f"  {num} - {label}")

    while True:
        o = input("Escolha: ").strip()
        if o in OBJETIVOS:
            dados["objetivo"] = OBJETIVOS[o]
            break
        print("  Opcao invalida.")

    salvar(dados)
    print(f"\nPerfil salvo! Bem-vindo(a), {dados['nome']}!")
    pausa()


# ──────────────────────────────────────────────
# EXIBICAO DE TREINOS
# ──────────────────────────────────────────────

def mostrar_treino(dia, objetivo, com_timer=False):
    """
    Mostra o treino de um dia especifico.

    Args:
        dia (str): Dia da semana.
        objetivo (str): Objetivo do treino.
        com_timer (bool): Se True, mostra timer de descanso.
    """
    musculos = SPLIT_SEMANAL[dia]
    label = OBJETIVOS_LABEL.get(objetivo, objetivo.upper())

    limpar_tela()
    linha()
    print(f"  TREINO DE {dia.upper()}")
    print(f"  Objetivo: {label}")
    print(f"  Foco: {musculos[0].upper()} + {musculos[1].upper()}")
    linha()

    for musculo in musculos:
        print(f"\n  --- {musculo.upper()} ---")
        lista = EXERCICIOS[musculo][objetivo]

        for nome, series, reps, desc in lista:
            print(f"    {nome}")
            print(f"      Series: {series} | Reps: {reps} | Descanso: {desc}s")

            if com_timer:
                timer_descanso(desc)

    pausa()


def timer_descanso(segundos):
    """Mostra contagem regressiva de descanso."""
    print(f"\n  Descanso: {segundos} segundos")
    for i in range(segundos, 0, -1):
        print(f"  {i}s ", end="\r")
        time.sleep(1)
    print("  Bora!                ")


def ver_treino_dia(dados):
    """Mostra treino de um dia escolhido."""
    limpar_tela()
    print("\nEscolha o dia:")
    for i, dia in enumerate(DIAS_SEMANA, 1):
        m = SPLIT_SEMANAL[dia]
        print(f"  {i} - {dia.upper()} ({m[0].upper()} + {m[1].upper()})")

    n = input("\nNumero: ").strip()
    if n.isdigit() and 1 <= int(n) <= len(DIAS_SEMANA):
        dia = DIAS_SEMANA[int(n) - 1]
        mostrar_treino(dia, dados["objetivo"])
    else:
        print("Opcao invalida.")
        pausa()


def ver_treino_semana(dados):
    """Mostra todos os treinos da semana."""
    limpar_tela()
    for dia in DIAS_SEMANA:
        mostrar_treino(dia, dados["objetivo"])


def treinar_com_timer(dados):
    """Mostra treino com timer de descanso entre series."""
    ver_treino_dia(dados)


# ──────────────────────────────────────────────
# HISTORICO E PESO
# ──────────────────────────────────────────────

def registrar_treino(dados):
    """Registra um treino feito."""
    limpar_tela()
    print("\nQual dia treinou?")
    for i, dia in enumerate(DIAS_SEMANA, 1):
        print(f"  {i} - {dia.upper()}")

    n = input("\nNumero: ").strip()
    if n.isdigit() and 1 <= int(n) <= len(DIAS_SEMANA):
        dia = DIAS_SEMANA[int(n) - 1]
        registro = {
            "dia": dia,
            "data": datetime.now().strftime("%d/%m/%Y %H:%M"),
        }
        dados["treinos_feitos"].append(registro)
        salvar(dados)
        print(f"\nTreino de {dia.upper()} registrado!")
    else:
        print("Opcao invalida.")
    pausa()


def ver_historico(dados):
    """Mostra historico de treinos."""
    limpar_tela()
    linha()
    print("  HISTORICO DE TREINOS")
    linha()

    if not dados["treinos_feitos"]:
        print("  Nenhum treino registrado ainda.")
    else:
        for i, t in enumerate(dados["treinos_feitos"], 1):
            print(f"  {i} - {t['dia'].upper()} ({t['data']})")

    print(f"\n  Total: {len(dados['treinos_feitos'])} treinos")
    pausa()


def registrar_peso(dados):
    """Registra o peso atual."""
    limpar_tela()
    print(f"\nPeso atual: {dados['peso']} kg")

    while True:
        try:
            novo_peso = float(input("Novo peso (kg): ").strip())
            if novo_peso > 0:
                break
            print("  Peso deve ser maior que 0.")
        except ValueError:
            print("  Digite um numero valido.")

    dados["peso"] = novo_peso
    registro = {
        "peso": novo_peso,
        "data": datetime.now().strftime("%d/%m/%Y %H:%M"),
    }
    dados["historico_peso"].append(registro)
    salvar(dados)
    print(f"Peso registrado: {novo_peso} kg")
    pausa()


def ver_evolucao_peso(dados):
    """Mostra evolucao do peso ao longo do tempo."""
    limpar_tela()
    linha()
    print("  EVOLUCAO DO PESO")
    linha()

    if not dados["historico_peso"]:
        print("  Nenhum peso registrado ainda.")
    else:
        for i, p in enumerate(dados["historico_peso"], 1):
            print(f"  {i} - {p['peso']} kg ({p['data']})")

        primeiro = dados["historico_peso"][0]["peso"]
        ultimo = dados["historico_peso"][-1]["peso"]
        diff = ultimo - primeiro

        if diff < 0:
            print(f"\n  Voce perdeu {abs(diff):.1f} kg!")
        elif diff > 0:
            print(f"\n  Voce ganhou {diff:.1f} kg")
        else:
            print(f"\n  Peso estavel")

    pausa()


def ver_imc(dados):
    """Mostra o IMC do usuario."""
    limpar_tela()
    imc, classificacao = calcular_imc(dados["peso"], dados["altura"])

    linha()
    print("  SEU IMC")
    linha()
    print(f"  Peso: {dados['peso']} kg")
    print(f"  Altura: {dados['altura']} m")
    print(f"  IMC: {imc:.1f}")
    print(f"  Classificacao: {classificacao}")
    pausa()


# ──────────────────────────────────────────────
# MENU PRINCIPAL
# ──────────────────────────────────────────────

def menu():
    """Exibe o menu principal e gerencia a navegacao."""
    dados = carregar()

    if not dados["nome"]:
        cadastrar_usuario(dados)

    imc, _ = calcular_imc(dados["peso"], dados["altura"])

    while True:
        limpar_tela()
        print(f"\nOla, {dados['nome']}!")
        print(f"Peso: {dados['peso']} kg | IMC: {imc:.1f} | Objetivo: {dados['objetivo'].upper()}")
        linha()
        print("  POWER FIT")
        linha()
        print("  1 - Ver treino do dia")
        print("  2 - Ver treino da semana")
        print("  3 - Treinar com timer")
        print("  4 - Registrar treino feito")
        print("  5 - Ver historico")
        print("  6 - Registrar peso")
        print("  7 - Ver evolucao do peso")
        print("  8 - Ver IMC")
        print("  9 - Atualizar perfil")
        print("  0 - Sair")
        linha()

        opcao = input("Escolha: ").strip()

        if opcao == "0":
            limpar_tela()
            print(f"Ate logo, {dados['nome']}! Bons treinos!")
            break
        elif opcao == "1":
            ver_treino_dia(dados)
        elif opcao == "2":
            ver_treino_semana(dados)
        elif opcao == "3":
            treinar_com_timer(dados)
        elif opcao == "4":
            registrar_treino(dados)
        elif opcao == "5":
            ver_historico(dados)
        elif opcao == "6":
            registrar_peso(dados)
        elif opcao == "7":
            ver_evolucao_peso(dados)
        elif opcao == "8":
            ver_imc(dados)
        elif opcao == "9":
            cadastrar_usuario(dados)


# ──────────────────────────────────────────────
# PONTO DE ENTRADA
# ──────────────────────────────────────────────

if __name__ == "__main__":
    menu()
