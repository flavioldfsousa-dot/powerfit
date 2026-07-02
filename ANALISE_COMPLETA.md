# ANALISE COMPLETA DO SISTEMA - POWER FIT

**Data:** 02/07/2026
**Analista:** Senior de Desenvolvimento de Sistemas
**Versao:** 0.1 (Em desenvolvimento)

---

## 1. VISAO GERAL DO SISTEMA

**Objetivo:** Aplicacao desktop para gerenciamento de treinos de academia, com foco em planos de treino semanais para diferentes objetivos (forca, definicao muscular, perda de peso).

**Stack:** Python 3.14 + customtkinter (GUI) + JSON (persistencia)

**Status atual:** Prototipo funcional em fase inicial de desenvolvimento.

---

## 2. ESTRUTURA DO PROJETO

### 2.1 Diretorios

```
/Users/annagabrielly/
├── gym-app/                    # Diretorio de aulas (copia parcial)
│   ├── aula01.py               # Aula 01 - Variaveis e listas
│   ├── aula02.py               # Aula 02 - Dicionarios e loops
│   ├── aula03.py               # Aula 03 - Menu interativo
│   ├── aula04.py               # Aula 04 - Persistencia JSON
│   ├── aula05.py               # Aula 05 - IMC e timer
│   ├── app.py                  # App principal (versao antiga)
│   ├── app.py.save             # Backup incorreto (lixo)
│   └── dados_usuario.json      # Dados do usuario
│
└── projeto 001/                # Diretorio principal do projeto
    ├── aula02.py               # Aula 02 (duplicada)
    ├── aula03.py               # Aula 03 (duplicada)
    ├── aula04.py               # Aula 04 (duplicada)
    ├── aula05.py               # Aula 05 (duplicada)
    ├── aula06.py               # App GUI com customtkinter
    ├── treino.py               # Aula 01 (renomeada)
    └── dados_usuario.json      # Dados do usuario
```

### 2.2 Arquivos Principais

| Arquivo | Descricao | Linhas | Status |
|---------|-----------|--------|--------|
| `aula06.py` | App GUI principal (customtkinter) | 345 | **PRINCIPAL** |
| `aula05.py` | App CLI avancado com IMC/timer | 311 | Funcional |
| `aula04.py` | App CLI com persistencia | 244 | Funcional |
| `aula03.py` | App CLI interativo | 181 | Funcional |
| `aula02.py` | Lista de treinos | 60 | Funcional |
| `treino.py` / `aula01.py` | Introducao Python | 32 | Funcional |
| `app.py` | App antigo (gym-app) | 259 | Obsoleto |

### 2.3 Dependencias

```
customtkinter>=6.0.0
```

**Nao existe:**
- `requirements.txt`
- `pyproject.toml`
- `Pipfile`
- `poetry.lock`
- Ambiente virtual

---

## 3. FUNCIONAMENTO DO SISTEMA

### 3.1 Fluxo Principal

1. App inicia → Verifica se usuario existe no JSON
2. Se nao existe → Tela de cadastro (nome, peso, altura, objetivo)
3. Menu principal com 8 opcoes
4. Usuario navega entre telas
5. Dados persistidos em JSON local

### 3.2 Funcionalidades Implementadas

| Funcionalidade | CLI (aula05) | GUI (aula06) |
|---------------|-------------|-------------|
| Cadastro de usuario | SIM | SIM |
| Ver treino do dia | SIM | SIM |
| Ver treino da semana | SIM | SIM |
| Treinar com timer | SIM | NAO |
| Registrar treino | SIM | SIM |
| Historico de treinos | SIM | SIM |
| Registrar peso | SIM | SIM |
| Evolucao do peso | SIM | SIM |
| Ver IMC | SIM | SIM |
| Atualizar perfil | SIM | SIM |

### 3.3 Regras de Negocio

- **Split semanal:** 2 musculos por dia, pernas 3x na semana
- **Objetivos:** Forca (hipertrofia), Definicao muscular, Perda de peso
- **Exercicios:** 6 grupos musculares, 3 objetivos cada
- **Dados persistidos:** nome, peso, altura, objetivo, historico de treinos, historico de peso

### 3.4 Musculos cobertos

- Peito, Costas, Bracos (biceps/triceps), Pernas, Ombro, Abdomen

---

## 4. BUGS IDENTIFICADOS

### BUG-001: Division by zero no IMC (CRITICO)
- **Arquivo:** `aula05.py:25`
- **Problema:** Se `altura == 0`, ocorre `ZeroDivisionError`
- **Causa:** `calcular_imc()` nao valida altura antes de dividir
- **Impacto:** Crash do programa
- **Status:** CORRIGIDO no `aula06.py:27`, pendente no `aula05.py`

### BUG-002: Dados hardcoded no registro de treino (MEDIO)
- **Arquivo:** `aula05.py:202`, `aula04.py:172`
- **Problema:** `data: "hoje"` em vez de data real
- **Causa:** Falta importacao de `datetime`
- **Impacto:** Historico sem data real
- **Status:** CORRIGIDO no `aula06.py:250` (usa `datetime.now()`)

### BUG-003: Arquivo app.py.save contem codigo invalido (BAIXO)
- **Arquivo:** `gym-app/app.py.save:260-293`
- **Problema:** Codigo Python misturado com comandos de terminal
- **Causa:** Copia incorreta durante desenvolvimento
- **Impacto:** Nenhum (arquivo nao e executado)
- **Status:** PENDENTE - deve ser removido

### BUG-004: Inconsistencia nos nomes dos musculos (MEDIO)
- **Arquivo:** `app.py` usa `biceps`/`triceps`, aula03-06 usam `bracos`
- **Problema:** Split semanal inconsistente entre versoes
- **Causa:** Evolucao do projeto sem refatoracao
- **Impacto:** Confusao no treino de bracos

### BUG-005: Tipo do campo peso inconsistente (MEDIO)
- **Arquivo:** `aula04.py:145`
- **Problema:** `dados["peso"] = input(...)` salva como string
- **Causa:** Falta `float()` na conversao
- **Impacto:** Calculo de IMC falha no aula04

### BUG-006: Falta validacao de entrada em cadastro (BAIXO)
- **Arquivos:** `aula05.py:167-169`, `aula06.py:163-165`
- **Problema:** Se usuario digitar texto no campo de peso/altura, crasha
- **Causa:** `float(input())` sem try/except no CLI
- **Impacto:** ValueError no terminal
- **Status:** Tratado no GUI (aula06), pendente no CLI (aula05)

### BUG-007: Codigo duplicado entre diretorios (BAIXO)
- **Problema:** Arquivos identicos em `gym-app/` e `projeto 001/`
- **Causa:** Copia manual durante aulas
- **Impacto:** Manutencao dificil, risko de divergencia

### BUG-008: Typo na variavel COR_TITulo (BAIXO)
- **Arquivo:** `aula06.py:213`
- **Problema:** `COR_TITulo if False else COR_TITULO` - expressao morta
- **Causa:** Codigo de debug nao removido
- **Impacto:** Funcional mas confuso

### BUG-009: Falta tratamento de erro no JSON (MEDIO)
- **Arquivos:** Todos com `json.load()`
- **Problema:** Se o JSON estiver corrompido, crasha
- **Causa:** Sem try/except na leitura
- **Impacto:** Perda de dados

### BUG-010: Caminhos hardcoded (CRITICO)
- **Arquivos:** Todos os arquivos
- **Problema:** `/Users/annagabrielly/...` em vez de caminhos relativos
- **Causa:** Falta使用 `os.path` ou `pathlib`
- **Impacto:** App nao funciona em outro computador

---

## 5. RISCOS DE SEGURANCA

### RISCO-001: Dados pessoais em JSON sem criptografia
- **Severidade:** ALTA
- **Problema:** Nome, peso, altura salvos em texto plano
- **Recomendacao:** Usar SQLite ou criptografar o JSON

### RISCO-002: Caminhos absolutos expostos
- **Severidade:** MEDIA
- **Problema:** Username do computador visivel no codigo
- **Recomendacao:** Usar variaveis de ambiente ou caminhos relativos

### RISCO-003: Sem tratamento de entrada (injection)
- **Severidade:** BAIXA (app desktop local)
- **Problema:** Inputs nao sanitizados
- **Recomendacao:** Validar e sanitizar entradas

---

## 6. QUALIDADE DO CODIGO

### 6.1 Problemas Encontrados

| Problema | Severidade | Arquivos |
|----------|-----------|----------|
| Codigo massivamente duplicado | ALTA | Todos |
| Sem separacao de responsabilidades | ALTA | Todos |
| Funcoes muito longas | MEDIA | aula05, aula06 |
| Sem docstrings | MEDIA | Todos |
| Sem type hints | MEDIA | Todos |
| Sem tratamento padrao de erros | MEDIA | Todos |
| Variaveis globais | MEDIA | Todos |
| Caminhos hardcoded | ALTA | Todos |
| Sem testes unitarios | ALTA | N/A |

### 6.2 Codigo Duplicado (Estimativa)

- **Banco de exercicios:** Duplicado em 8 arquivos (~500 linhas cada)
- **Split semanal:** Duplicado em 8 arquivos
- **Funcoes de menu:** Duplicadas em 5 arquivos
- **Estimativa total:** ~70% do codigo e duplicado

---

## 7. PERFORMANCE

- **Arquivo JSON:** Leitura/escrita a cada operacao (pode causar lentidao com muitos dados)
- **Sem cache:** Dados recarregados constantemente
- **GUI:** customtkinter e mais lento que tkinter puro
- **Impacto:** Baixo para uso individual, alto se escalar

---

## 8. TESTES

- **Testes unitarios:** NENHUM
- **Testes de integracao:** NENHUM
- **Testes E2E:** NENHUM
- **Cobertura:** 0%

---

## 9. DEVOPS E PRODUCAO

- **Docker:** NAO
- **CI/CD:** NAO
- **Deploy:** NAO
- **Logs:** NAO
- **Monitoramento:** NAO
- **Backup:** NAO

---

## 10. O QUE JA ESTA PRONTO

- [x] Banco de exercicios por musculo e objetivo
- [x] Split semanal (2 musculos/dia, pernas 3x)
- [x] Cadastro de usuario (nome, peso, altura, objetivo)
- [x] Persistencia em JSON
- [x] Visualizacao de treinos (dia/semana)
- [x] Registro de treinos feitos
- [x] Historico de treinos
- [x] Registro de peso
- [x] Evolucao do peso
- [x] Calculo de IMC
- [x] Timer de descanso (CLI)
- [x] Interface grafica basica (GUI)

---

## 11. O QUE FALTA IMPLEMENTAR

### Obrigatorio
- [ ] Refatoracao completa (unificar diretorios)
- [ ] requirements.txt
- [ ] Caminhos relativos (remover hardcoded paths)
- [ ] Tratamento de erros padrao
- [ ] Docstrings em todas as funcoes
- [ ] Separacao de dados (exercicios em JSON externo)
- [ ] Validacao de entrada robusta
- [ ] Backup automatico dos dados

### Importante
- [ ] Testes unitarios
- [ ] Timer na GUI (aula06)
- [ ] Graficos de evolucao de peso
- [ ] Exportar treinos em PDF/CSV
- [ ] Multi-usuario
- [ ] Senha/protecao de perfil

### Desejavel
- [ ] App mobile (Kivy/Flutter)
- [ ] Backend com API REST
- [ ] Banco de dados relacional (SQLite/PostgreSQL)
- [ ] Sincronizacao na nuvem
- [ ] Notificacoes push
- [ ] Modo offline/online

---

## 12. PERCENTUAL DE CONCLUSAO

| Modulo | Percentual |
|--------|-----------|
| Banco de exercicios | 100% |
| Logica de treinos | 100% |
| Persistencia JSON | 80% |
| CLI completo | 90% |
| GUI basica | 70% |
| Tratamento de erros | 30% |
| Testes | 0% |
| Documentacao | 10% |
| DevOps | 0% |
| **GERAL** | **~45%** |

---

## 13. CHECKLIST FINAL PARA VERSAO 1.0

- [ ] Unificar diretorios (apenas `projeto 001/`)
- [ ] Criar `requirements.txt`
- [ ] Remover `app.py.save` e arquivos duplicados
- [ ] Usar caminhos relativos
- [ ] Adicionar tratamento de erros em todas as entradas
- [ ] Adicionar docstrings
- [ ] Criar exercicios.py separado
- [ ] Criar config.py para configuracoes
- [ ] Adicionar testes minimos
- [ ] Documentar como rodar
- [ ] Criar .gitignore

---

## 14. ROADMAP DE ENTREGA

### Fase 1 - Limpeza (1-2 dias)
- Remover duplicatas
- Unificar diretorios
- Corrigir bugs criticos
- Criar requirements.txt

### Fase 2 - Refatoracao (2-3 dias)
- Separar dados de exercicios
- Usar caminhos relativos
- Adicionar tratamento de erros
- Adicionar docstrings

### Fase 3 - Funcionalidades (3-5 dias)
- Timer na GUI
- Graficos de evolucao
- Exportacao de treinos
- Validacao robusta

### Fase 4 - Qualidade (2-3 dias)
- Testes unitarios
- Documentacao completa
- Preparar para deploy

### Fase 5 - Producao (1-2 dias)
- Docker
- CI/CD basico
- Deploy

---

## 15. HISTORICO DE DECISOES TECNICAS

| Data | Decisao | Motivo |
|------|---------|--------|
| 02/07/2026 | Escolha de customtkinter | GUI moderna e simples |
| 02/07/2026 | JSON como persistencia | Simplicidade para app local |
| 02/07/2026 | Split 2 musculos/dia | Padrao de treino eficiente |
| 02/07/2026 | Pernas 3x/semana | Recomendacao de treino |

---

*Documento gerado automaticamente pela analise do sistema.*
