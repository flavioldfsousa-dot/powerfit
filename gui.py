"""
gui.py - Interface grafica do Power Fit.

Modulo principal para uso com interface grafica (customtkinter).
Execute: python3 gui.py
"""

import os
import sys

# Adiciona o diretorio do projeto ao path
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

import customtkinter as ctk
from tkinter import messagebox
from datetime import datetime

from config import (
    APP_TITULO, APP_LARGURA, APP_ALTURA,
    COR_BG, COR_CARD, COR_TEXTO, COR_TITULO, COR_BTN, COR_BTN_HOVER, COR_DESTAQUE,
)
from exercicios import EXERCICIOS, SPLIT_SEMANAL, DIAS_SEMANA
from database import carregar, salvar, calcular_imc

# Configuracao do customtkinter
ctk.set_appearance_mode("dark")
ctk.set_default_color_theme("blue")


class PowerFitApp(ctk.CTk):
    """Janela principal do aplicativo Power Fit."""

    def __init__(self):
        super().__init__()
        self.title(APP_TITULO)
        self.geometry(f"{APP_LARGURA}x{APP_ALTURA}")
        self.configure(fg_color=COR_BG)

        self.dados = carregar()

        # Header
        self.header = ctk.CTkFrame(self, fg_color=COR_BG, height=80)
        self.header.pack(fill="x", padx=20, pady=(15, 0))

        self.titulo = ctk.CTkLabel(
            self.header, text=APP_TITULO,
            font=("Helvetica", 28, "bold"), text_color=COR_TITULO,
        )
        self.titulo.pack(side="left")

        self.subtitle = ctk.CTkLabel(
            self.header, text="",
            font=("Helvetica", 11), text_color=COR_TEXTO,
        )
        self.subtitle.pack(side="right", pady=10)

        # Frame com scroll
        self.scroll_frame = ctk.CTkFrame(self, fg_color=COR_BG)
        self.scroll_frame.pack(fill="both", expand=True, padx=20, pady=10)

        self.canvas = ctk.CTkCanvas(self.scroll_frame, bg=COR_BG, highlightthickness=0)
        self.scrollbar = ctk.CTkScrollbar(self.scroll_frame, orientation="vertical", command=self.canvas.yview)
        self.inner = ctk.CTkFrame(self.canvas, fg_color=COR_BG)

        self.inner.bind("<Configure>", lambda e: self.canvas.configure(scrollregion=self.canvas.bbox("all")))
        self.canvas.create_window((0, 0), window=self.inner, anchor="nw")
        self.canvas.configure(yscrollcommand=self.scrollbar.set)

        self.canvas.pack(side="left", fill="both", expand=True)
        self.scrollbar.pack(side="right", fill="y")

        self.canvas.bind_all("<MouseWheel>", lambda e: self.canvas.yview_scroll(int(-1 * (e.delta / 120)), "units"))

        # Inicia o app
        if not self.dados["nome"]:
            self.tela_cadastro()
        else:
            self.tela_menu()

    # ──────────────────────────────────────────
    # UTILIDADES
    # ──────────────────────────────────────────

    def limpar(self):
        """Remove todos os widgets do frame interno."""
        for w in self.inner.winfo_children():
            w.destroy()

    def atualizar_header(self):
        """Atualiza as informacoes do cabecalho."""
        imc, _ = calcular_imc(self.dados["peso"], self.dados["altura"])
        self.subtitle.configure(text=f"{self.dados['nome']} | {self.dados['peso']}kg | IMC {imc:.1f}")

    def criar_botao(self, parent, texto, comando, cor=COR_BTN, width=380, height=40):
        """Cria um botao padronizado."""
        return ctk.CTkButton(
            parent, text=texto, command=comando,
            fg_color=cor, hover_color=COR_BTN_HOVER,
            text_color=COR_TEXTO, font=("Helvetica", 13, "bold"),
            width=width, height=height, corner_radius=8,
        )

    def criar_campo(self, parent, texto):
        """Cria um campo de entrada padronizado."""
        ctk.CTkLabel(parent, text=texto, font=("Helvetica", 12), text_color=COR_TEXTO).pack(anchor="w", pady=(10, 2))
        entry = ctk.CTkEntry(
            parent, placeholder_text="",
            fg_color=COR_BTN, border_color=COR_BTN_HOVER,
            text_color=COR_TEXTO, font=("Helvetica", 13),
            height=38, corner_radius=6,
        )
        entry.pack(fill="x", pady=(0, 5))
        return entry

    # ──────────────────────────────────────────
    # TELAS
    # ──────────────────────────────────────────

    def tela_cadastro(self):
        """Tela de cadastro de usuario."""
        self.limpar()
        ctk.CTkLabel(self.inner, text="CADASTRAR PERFIL", font=("Helvetica", 20, "bold"), text_color=COR_TITULO).pack(pady=(15, 20))

        card = ctk.CTkFrame(self.inner, fg_color=COR_CARD, corner_radius=12)
        card.pack(fill="x", padx=10, pady=5)

        self.entry_nome = self.criar_campo(card, "Nome")
        self.entry_peso = self.criar_campo(card, "Peso (kg)")
        self.entry_altura = self.criar_campo(card, "Altura (m) - ex: 1.65")

        ctk.CTkLabel(card, text="Objetivo:", font=("Helvetica", 12), text_color=COR_TEXTO).pack(anchor="w", pady=(10, 5))
        self.obj_var = ctk.StringVar(value="Forca")
        for obj in ["Forca", "Definicao", "Perda de Peso"]:
            ctk.CTkRadioButton(
                card, text=obj, variable=self.obj_var, value=obj,
                fg_color=COR_TITULO, hover_color=COR_BTN_HOVER,
                text_color=COR_TEXTO, font=("Helvetica", 12),
            ).pack(anchor="w", pady=2)

        self.criar_botao(card, "SALVAR PERFIL", self._salvar_perfil, COR_TITULO).pack(pady=20)

    def _salvar_perfil(self):
        """Salva os dados do perfil."""
        mapa = {"Forca": "forca", "Definicao": "definicao", "Perda de Peso": "perda_de_peso"}

        try:
            self.dados["nome"] = self.entry_nome.get().strip()
            self.dados["peso"] = float(self.entry_peso.get().strip())
            self.dados["altura"] = float(self.entry_altura.get().strip())
            self.dados["objetivo"] = mapa[self.obj_var.get()]

            if not self.dados["nome"]:
                raise ValueError("Nome obrigatorio")

            salvar(self.dados)
            self.atualizar_header()
            self.tela_menu()
        except ValueError:
            messagebox.showerror("Erro", "Preencha todos os campos corretamente!")

    def tela_menu(self):
        """Tela do menu principal."""
        self.limpar()
        self.atualizar_header()

        ctk.CTkLabel(self.inner, text="MENU PRINCIPAL", font=("Helvetica", 18, "bold"), text_color=COR_TEXTO).pack(pady=(10, 15))

        opcoes = [
            ("Treino do Dia", self.tela_treino_dia, COR_BTN),
            ("Treino da Semana", self.tela_treino_semana, COR_BTN),
            ("Registrar Treino", self.tela_registrar_treino, COR_DESTAQUE),
            ("Historico de Treinos", self.tela_historico, COR_BTN),
            ("Registrar Peso", self.tela_registrar_peso, COR_BTN),
            ("Evolucao do Peso", self.tela_evolucao, COR_BTN),
            ("Ver IMC", self.tela_imc, COR_BTN),
            ("Atualizar Perfil", self.tela_cadastro, COR_BTN),
        ]

        for texto, cmd, cor in opcoes:
            self.criar_botao(self.inner, texto, cmd, cor).pack(pady=4)

    def tela_treino_dia(self):
        """Tela de selecao de dia para treino."""
        self.limpar()
        ctk.CTkLabel(self.inner, text="ESCOLHA O DIA", font=("Helvetica", 18, "bold"), text_color=COR_TITULO).pack(pady=(10, 15))

        for dia in DIAS_SEMANA:
            m = SPLIT_SEMANAL[dia]
            texto = f"{dia.upper()}  |  {m[0].upper()} + {m[1].upper()}"
            self.criar_botao(self.inner, texto, lambda d=dia: self._mostrar_treino(d)).pack(pady=4)

        self.criar_botao(self.inner, "VOLTAR", self.tela_menu, COR_TITULO).pack(pady=15)

    def _mostrar_treino(self, dia):
        """Mostra os exercicios de um dia."""
        self.limpar()
        obj = self.dados["objetivo"]
        musculos = SPLIT_SEMANAL[dia]

        ctk.CTkLabel(self.inner, text=f"TREINO DE {dia.upper()}", font=("Helvetica", 20, "bold"), text_color=COR_TITULO).pack(pady=(10, 5))
        ctk.CTkLabel(self.inner, text=f"Objetivo: {obj.upper()}", font=("Helvetica", 12), text_color=COR_DESTAQUE).pack(pady=(0, 10))

        for musculo in musculos:
            card = ctk.CTkFrame(self.inner, fg_color=COR_CARD, corner_radius=10)
            card.pack(fill="x", padx=10, pady=5)

            ctk.CTkLabel(card, text=musculo.upper(), font=("Helvetica", 14, "bold"), text_color=COR_TITULO).pack(anchor="w", padx=15, pady=(10, 5))

            for nome, series, reps, desc in EXERCICIOS[musculo][obj]:
                frame = ctk.CTkFrame(card, fg_color="transparent")
                frame.pack(fill="x", padx=15, pady=2)

                ctk.CTkLabel(frame, text=nome, font=("Helvetica", 11, "bold"), text_color=COR_TEXTO).pack(anchor="w")
                ctk.CTkLabel(frame, text=f"  {series} series  |  {reps} reps  |  {desc}s descanso", font=("Helvetica", 10), text_color="#8b949e").pack(anchor="w")

        self.criar_botao(self.inner, "VOLTAR", self.tela_treino_dia, COR_TITULO).pack(pady=15)

    def tela_treino_semana(self):
        """Mostra todos os treinos da semana."""
        self.limpar()
        obj = self.dados["objetivo"]

        ctk.CTkLabel(self.inner, text="SUA SEMANA COMPLETA", font=("Helvetica", 18, "bold"), text_color=COR_TITULO).pack(pady=(10, 15))

        for dia in DIAS_SEMANA:
            m = SPLIT_SEMANAL[dia]
            card = ctk.CTkFrame(self.inner, fg_color=COR_CARD, corner_radius=10)
            card.pack(fill="x", padx=10, pady=5)

            ctk.CTkLabel(card, text=f"{dia.upper()} - {m[0].upper()} + {m[1].upper()}", font=("Helvetica", 12, "bold"), text_color=COR_DESTAQUE).pack(anchor="w", padx=15, pady=(10, 5))

            for musculo in m:
                for nome, series, reps, desc in EXERCICIOS[musculo][obj]:
                    ctk.CTkLabel(card, text=f"  {nome}  ({series}x{reps})", font=("Helvetica", 10), text_color=COR_TEXTO).pack(anchor="w", padx=15, pady=1)

        self.criar_botao(self.inner, "VOLTAR", self.tela_menu, COR_TITULO).pack(pady=15)

    def tela_registrar_treino(self):
        """Tela para registrar treino feito."""
        self.limpar()
        ctk.CTkLabel(self.inner, text="REGISTRAR TREINO", font=("Helvetica", 18, "bold"), text_color=COR_TITULO).pack(pady=(10, 15))

        for dia in DIAS_SEMANA:
            self.criar_botao(self.inner, dia.upper(), lambda d=dia: self._registrar(d)).pack(pady=4)

        self.criar_botao(self.inner, "VOLTAR", self.tela_menu, COR_TITULO).pack(pady=15)

    def _registrar(self, dia):
        """Registra o treino no JSON."""
        reg = {"dia": dia, "data": datetime.now().strftime("%d/%m/%Y %H:%M")}
        self.dados["treinos_feitos"].append(reg)
        salvar(self.dados)
        messagebox.showinfo("Sucesso", f"Treino de {dia.upper()} registrado!")
        self.tela_menu()

    def tela_historico(self):
        """Mostra historico de treinos."""
        self.limpar()
        ctk.CTkLabel(self.inner, text="HISTORICO DE TREINOS", font=("Helvetica", 18, "bold"), text_color=COR_TITULO).pack(pady=(10, 15))

        if not self.dados["treinos_feitos"]:
            ctk.CTkLabel(self.inner, text="Nenhum treino registrado.", font=("Helvetica", 12), text_color=COR_TEXTO).pack()
        else:
            for i, t in enumerate(self.dados["treinos_feitos"], 1):
                card = ctk.CTkFrame(self.inner, fg_color=COR_CARD, corner_radius=8)
                card.pack(fill="x", padx=10, pady=3)
                ctk.CTkLabel(card, text=f"{i}. {t['dia'].upper()}", font=("Helvetica", 11, "bold"), text_color=COR_TEXTO).pack(side="left", padx=15, pady=8)
                ctk.CTkLabel(card, text=t["data"], font=("Helvetica", 10), text_color="#8b949e").pack(side="right", padx=15)

            ctk.CTkLabel(self.inner, text=f"\nTotal: {len(self.dados['treinos_feitos'])} treinos", font=("Helvetica", 13, "bold"), text_color=COR_DESTAQUE).pack()

        self.criar_botao(self.inner, "VOLTAR", self.tela_menu, COR_TITULO).pack(pady=15)

    def tela_registrar_peso(self):
        """Tela para registrar peso."""
        self.limpar()
        ctk.CTkLabel(self.inner, text="REGISTRAR PESO", font=("Helvetica", 18, "bold"), text_color=COR_TITULO).pack(pady=(10, 15))
        ctk.CTkLabel(self.inner, text=f"Peso atual: {self.dados['peso']} kg", font=("Helvetica", 13), text_color=COR_TEXTO).pack(pady=5)

        card = ctk.CTkFrame(self.inner, fg_color=COR_CARD, corner_radius=10)
        card.pack(fill="x", padx=10, pady=10)

        self.entry_peso_novo = self.criar_campo(card, "Novo peso (kg)")

        def salvar_peso():
            try:
                novo = float(self.entry_peso_novo.get().strip())
                if novo <= 0:
                    raise ValueError
                self.dados["peso"] = novo
                self.dados["historico_peso"].append({"peso": novo, "data": datetime.now().strftime("%d/%m/%Y %H:%M")})
                salvar(self.dados)
                messagebox.showinfo("Sucesso", f"Peso registrado: {novo} kg")
                self.tela_menu()
            except ValueError:
                messagebox.showerror("Erro", "Digite um numero valido!")

        self.criar_botao(card, "SALVAR", salvar_peso, COR_TITULO).pack(pady=10)
        self.criar_botao(self.inner, "VOLTAR", self.tela_menu, COR_TITULO).pack(pady=10)

    def tela_evolucao(self):
        """Mostra evolucao do peso."""
        self.limpar()
        ctk.CTkLabel(self.inner, text="EVOLUCAO DO PESO", font=("Helvetica", 18, "bold"), text_color=COR_TITULO).pack(pady=(10, 15))

        if not self.dados["historico_peso"]:
            ctk.CTkLabel(self.inner, text="Nenhum peso registrado.", font=("Helvetica", 12), text_color=COR_TEXTO).pack()
        else:
            for p in self.dados["historico_peso"]:
                card = ctk.CTkFrame(self.inner, fg_color=COR_CARD, corner_radius=8)
                card.pack(fill="x", padx=10, pady=3)
                ctk.CTkLabel(card, text=f"{p['peso']} kg", font=("Helvetica", 12, "bold"), text_color=COR_TEXTO).pack(side="left", padx=15, pady=8)
                ctk.CTkLabel(card, text=p["data"], font=("Helvetica", 10), text_color="#8b949e").pack(side="right", padx=15)

            primeiro = self.dados["historico_peso"][0]["peso"]
            ultimo = self.dados["historico_peso"][-1]["peso"]
            diff = ultimo - primeiro

            if diff < 0:
                texto, cor = f"Voce perdeu {abs(diff):.1f} kg!", "#2ea043"
            elif diff > 0:
                texto, cor = f"Voce ganhou {diff:.1f} kg", COR_TITULO
            else:
                texto, cor = "Peso estavel", COR_DESTAQUE

            ctk.CTkLabel(self.inner, text=f"\n{texto}", font=("Helvetica", 14, "bold"), text_color=cor).pack()

        self.criar_botao(self.inner, "VOLTAR", self.tela_menu, COR_TITULO).pack(pady=15)

    def tela_imc(self):
        """Mostra o IMC."""
        self.limpar()
        imc, cls = calcular_imc(self.dados["peso"], self.dados["altura"])

        ctk.CTkLabel(self.inner, text="SEU IMC", font=("Helvetica", 20, "bold"), text_color=COR_TITULO).pack(pady=(15, 10))

        card = ctk.CTkFrame(self.inner, fg_color=COR_CARD, corner_radius=12)
        card.pack(fill="x", padx=20, pady=10)

        ctk.CTkLabel(card, text=f"Peso: {self.dados['peso']} kg", font=("Helvetica", 13), text_color=COR_TEXTO).pack(pady=5)
        ctk.CTkLabel(card, text=f"Altura: {self.dados['altura']} m", font=("Helvetica", 13), text_color=COR_TEXTO).pack(pady=5)
        ctk.CTkLabel(card, text=f"{imc:.1f}", font=("Helvetica", 48, "bold"), text_color=COR_DESTAQUE).pack(pady=10)
        ctk.CTkLabel(card, text=cls, font=("Helvetica", 16, "bold"), text_color=COR_TITULO).pack(pady=5)

        self.criar_botao(self.inner, "VOLTAR", self.tela_menu, COR_TITULO).pack(pady=15)


# ──────────────────────────────────────────────
# PONTO DE ENTRADA
# ──────────────────────────────────────────────

if __name__ == "__main__":
    app = PowerFitApp()
    app.mainloop()
