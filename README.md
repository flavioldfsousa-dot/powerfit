# 💪 Zentrox Fit

Aplicativo de treino personalizado com suporte a multiplos idiomas.

## 📱 Downloads

### 🤖 Android (APK)
**[BAIXAR APK](https://github.com/SEU_USUARIO/projeto-001/releases/latest/download/ZentroxFit.apk)**

### 🍎 iOS
**[VER INSTRUCOES DE INSTALACAO](ios/)**

### 🌐 Web App
```bash
cd web
python3 app.py
# Acesse: http://localhost:5001
```

---

## 📂 Onde Estao os Builds

```
projeto 001/
├── releases/                    # 📦 DOWNLOADS
│   ├── android/
│   │   └── ZentroxFit.apk      # 🤖 APK Android
│   ├── ios/
│   │   ├── ZentroxFit.ipa      # 🍎 IPA iOS
│   │   └── INSTALL.md          # Instrucoes iOS
│   ├── index.html              # Pagina de downloads
│   ├── build-android.sh        # Script build Android
│   ├── build-ios.sh            # Script build iOS
│   └── README.md               # Instrucoes completas
│
├── app/                         # 🤖 Codigo Android (Kotlin)
├── ios/                         # 🍎 Codigo iOS (Swift)
├── web/                         # 🌐 Web App (Flask)
└── .github/workflows/           # ⚙️ CI/CD
    ├── build.yml               # Build Android automatico
    └── build-ios.yml           # Build iOS automatico
```

---

## 🚀 Como Buildar

### Android (Local)
```bash
cd releases
./build-android.sh
# APK sera gerado em: releases/android/ZentroxFit.apk
```

### iOS (Local)
```bash
cd releases
./build-ios.sh
# Necessario Xcode instalado
```

### Via GitHub Actions (Automatico)
1. Faca push para o branch `main`
2. O GitHub Actions ira buildar automaticamente
3. Os APKs estarao em: `releases/`
4. Acesse: `https://github.com/SEU_USUARIO/projeto-001/releases`

---

## 📱 Como Instalar

### Android
1. Baixe o `ZentroxFit.apk`
2. Va em **Configuracoes > Seguranca**
3. Ative **"Fontes desconhecidas"**
4. Abra o APK e instale

### iOS (via Xcode)
1. Clone o repositorio
2. Abra `ios/ZentroxFit.xcodeproj` no Xcode
3. Configure sua Apple Developer account
4. Selecione seu dispositivo
5. Clique em **Build & Run**

### iOS (Web App)
1. Execute o servidor web
2. Abra no Safari: `http://localhost:5001`
3. Toque em **"Adicionar a Tela Inicial"**

---

## 🌍 Idiomas Suportados

| Bandeira | Idioma | Status |
|---------|--------|--------|
| 🇧🇷 | Português (BR) | ✅ |
| 🇺🇸 | English (US) | ✅ |
| 🇪🇸 | Español | ✅ |
| 🇫🇷 | Français | ✅ |
| 🇩🇪 | Deutsch | ✅ |
| 🇯🇵 | 日本語 | ✅ |

---

## 🎯 Funcionalidades

- ✅ Treinos por grupo muscular
- ✅ Personalizacao de exercicios
- ✅ Suporte a 6 idiomas
- ✅ Calculadora de IMC
- ✅ Registro de peso
- ✅ Historico de treinos
- ✅ Timer com presets
- ✅ Assistente IA
- ✅ Tema escuro estilo iOS

---

## 🛠️ Tecnologias

| Plataforma | Tecnologias |
|-----------|-------------|
| 🤖 Android | Kotlin, Android SDK, Fragments |
| 🍎 iOS | Swift, UIKit, WKWebView |
| 🌐 Web | Python Flask, HTML/CSS/JS |

---

## 📋 Requisitos

### Android
- Android 7.0+ (API 24)
- Java 17+ (para build)

### iOS
- iOS 15.0+
- Xcode 15.0+ (para build)

### Web
- Python 3.8+
- Navegador web moderno

---

## 🔧 Desenvolvimento

### Rodar Web App
```bash
cd web
pip install flask
python3 app.py
```

### Build Android
```bash
cd app
./gradlew assembleDebug
```

### Build iOS
```bash
cd ios
open ZentroxFit.xcodeproj
```

---

## 📄 Licenca

PowerFit App - Todos os direitos reservados.
