"""
database.py - Modulo de persistencia de dados do Power Fit.

Responsavel por carregar, salvar e gerenciar os dados do usuario
em formato JSON.
"""

import json
import os
from config import ARQUIVO_USUARIO


def _defaults():
    """Retorna a estrutura padrao de dados do usuario."""
    return {
        "nome": "",
        "peso": 0.0,
        "altura": 0.0,
        "objetivo": "forca",
        "treinos_feitos": [],
        "historico_peso": [],
    }


def carregar():
    """
    Carrega os dados do usuario do arquivo JSON.

    Retorna:
        dict: Dados do usuario ou estrutura padrao se nao existir.
    """
    if os.path.exists(ARQUIVO_USUARIO):
        try:
            with open(ARQUIVO_USUARIO, "r", encoding="utf-8") as f:
                dados = json.load(f)
                # Compatibilidade com versoes anteriores
                dados.setdefault("peso", 0.0)
                dados.setdefault("altura", 0.0)
                dados.setdefault("treinos_feitos", [])
                dados.setdefault("historico_peso", [])
                return dados
        except (json.JSONDecodeError, IOError):
            return _defaults()
    return _defaults()


def salvar(dados):
    """
    Salva os dados do usuario no arquivo JSON.

    Args:
        dados (dict): Dados a serem salvos.
    """
    try:
        with open(ARQUIVO_USUARIO, "w", encoding="utf-8") as f:
            json.dump(dados, f, indent=2, ensure_ascii=False)
    except IOError as e:
        print(f"Erro ao salvar dados: {e}")


def calcular_imc(peso, altura):
    """
    Calcula o IMC e retorna a classificacao.

    Args:
        peso (float): Peso em kg.
        altura (float): Altura em metros.

    Returns:
        tuple: (imc, classificacao)
    """
    if altura <= 0:
        return 0.0, "Altura invalida"

    imc = peso / (altura * altura)

    if imc < 18.5:
        classificacao = "Abaixo do peso"
    elif imc < 25:
        classificacao = "Peso normal"
    elif imc < 30:
        classificacao = "Sobrepeso"
    else:
        classificacao = "Obesidade"

    return imc, classificacao
