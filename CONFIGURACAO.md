# Power Fit - Configuracao do Projeto

## GitHub
- **Repositorio:** https://github.com/flavioldfsousa-dot/powerfit
- **Conta:** flavioldfsousa-dot
- **Branch:** main

## Arquivos do Projeto

```
projeto 001/
├── main.py              # App mobile (Kivy) - Android APK
├── app.py               # App CLI (terminal)
├── gui.py               # App desktop (customtkinter)
├── exercicios.py        # Banco de exercicios (dados)
├── config.py            # Configuracoes do projeto
├── database.py          # Persistencia JSON
├── buildozer.spec       # Config para gerar APK Android
├── requirements.txt     # Dependencias Python
├── .gitignore           # Arquivos ignorados pelo git
├── .github/workflows/
│   └── build.yml        # GitHub Actions (gera APK automatico)
├── setup.sh             # Script de configuracao
├── presplash.png        # Splash screen (512x512)
├── icon.png             # Icone do app (512x512)
└── data/                # Pasta de dados do usuario
```

## Como Usar

### App CLI (terminal)
```bash
python3 app.py
```

### App Desktop (janela)
```bash
python3 gui.py
```

### App Mobile (Kivy - testar no Mac)
```bash
~/.pyenv/versions/3.13.14/bin/python3 main.py
```

### Gerar APK Android
O APK e gerado automaticamente via GitHub Actions.
Acesse: https://github.com/flavioldfsousa-dot/powerfit/actions

Para gerar manualmente (requer Linux):
```bash
buildozer android debug
```

## Funcionalidades

| # | Funcao | CLI | GUI | Mobile |
|---|--------|-----|-----|--------|
| 1 | Treino do dia | OK | OK | OK |
| 2 | Treino da semana | OK | OK | OK |
| 3 | Timer de descanso | OK | - | OK |
| 4 | Registrar treino | OK | OK | - |
| 5 | Historico | OK | OK | OK |
| 6 | Registrar peso | OK | OK | OK |
| 7 | Evolucao do peso | OK | OK | OK |
| 8 | Ver IMC | OK | OK | OK |
| 9 | Atualizar perfil | OK | OK | OK |

## Stack Tecnico

- **Python 3.13** (para Kivy)
- **Python 3.14** (para CLI/GUI)
- **Kivy 2.3.1** (framework mobile)
- **customtkinter** (interface desktop)
- **Buildozer** (gerador de APK)
- **GitHub Actions** (CI/CD na nuvem)

## Historico de Commits

1. Power Fit v1.0 - App de Treinos
2. Fix buildozer.spec - add [app] section
3. Fix buildozer - API 31, cache, kivy pinned
4. Fix Java 17 for Gradle
