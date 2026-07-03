# Zentrox Fit - Configuracao do Projeto

## GitHub
- **Repositorio:** https://github.com/flavioldfsousa-dot/powerfit
- **Conta:** flavioldfsousa-dot
- **Branch:** main

## Arquivos do Projeto

```
projeto 001/
├── app/src/main/java/com/powerfit/app/
│   ├── SplashActivity.kt          # Tela de splash animada
│   ├── MainActivity.kt            # Activity principal
│   ├── data/
│   │   ├── DatabaseHelper.kt      # Persistencia SharedPreferences
│   │   ├── Exercicios.kt          # Banco de exercicios (90 exercicios)
│   │   └── AssistenteIA.kt        # Assistente IA offline
│   └── ui/
│       ├── CadastroFragment.kt    # Cadastro do usuario
│       ├── MenuFragment.kt        # Menu principal
│       ├── TreinoDiaFragment.kt   # Treino do dia
│       ├── TreinoSemanaFragment.kt# Plano semanal
│       ├── TimerFragment.kt       # Timer de treino
│       ├── HistoricoFragment.kt   # Historico
│       ├── RegistrarPesoFragment.kt# Registrar peso
│       ├── EvolucaoPesoFragment.kt# Evolucao do peso
│       ├── ImcFragment.kt         # Calculo de IMC
│       ├── PerfilFragment.kt      # Perfil do usuario
│       └── AssistenteFragment.kt  # Chat com IA
├── app/src/main/res/
│   ├── layout/                    # 13 layouts XML modernos
│   ├── drawable/                  # 15 drawables (cards, botoes, icones)
│   ├── anim/                      # 7 animacoes
│   ├── color/                     # Color state list
│   ├── menu/                      # Menu de navegacao
│   └── mipmap-*/                  # Icones do app
├── .github/workflows/build.yml    # GitHub Actions (gera APK)
└── app/build.gradle.kts           # Config Gradle
```

## Stack Tecnico

- **Kotlin** (linguagem principal)
- **Gradle 8.5** (sistema de build)
- **Material Design 3** (componentes UI)
- **SharedPreferences + Gson** (persistencia)
- **GitHub Actions** (CI/CD)

## Funcionalidades

| # | Funcao | Status |
|---|--------|--------|
| 1 | Splash screen animada | OK |
| 2 | Cadastro de perfil | OK |
| 3 | Menu principal moderno | OK |
| 4 | Treino do dia com cards | OK |
| 5 | 90 exercicios com alternativas | OK |
| 6 | Plano semanal | OK |
| 7 | Timer de treino | OK |
| 8 | Historico de treinos | OK |
| 9 | Registro de peso | OK |
| 10 | Evolucao do peso | OK |
| 11 | Calculo de IMC | OK |
| 12 | Atualizar perfil | OK |
| 13 | Assistente IA offline | OK |
| 14 | Navegacao com back button | OK |

## Como Gerar APK

O APK e gerado automaticamente via GitHub Actions.
Acesse: https://github.com/flavioldfsousa-dot/powerfit/actions

## Historico

1. Zentrox Fit v2.0 - Redesign completo + splash + 90 exercicios
2. Zentrox Fit v1.1 - Assistente IA + back button
3. Zentrox Fit v1.0 - App nativo Android (Kotlin + Gradle)
