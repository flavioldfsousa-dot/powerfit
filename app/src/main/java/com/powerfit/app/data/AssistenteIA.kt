package com.powerfit.app.data

import android.content.Context
import java.util.*

object AssistenteIA {

    data class Resposta(val texto: String, val sugestoes: List<String> = emptyList())

    private var ultimoTopico: String = ""
    private var contadorInteracoes: Int = 0

    fun responder(pergunta: String, context: Context): Resposta {
        val p = pergunta.lowercase().trim()
        val usuario = DatabaseHelper.carregar(context)
        val dia = obterDiaSemana()
        contadorInteracoes++

        if (p.contains("mais") || p.contains("outro") || p.contains("mais uma")) {
            ultimoTopico.let { topico ->
                val respostaTopico = when (topico) {
                    "exercicio_tecnica" -> exercicioTecnicaAleatorio(usuario)
                    "suplementacao" -> suplementacaoDetalhada()
                    "cardio" -> cardioVsForca(usuario)
                    "aquecimento" -> aquecimentoDetalhado()
                    "alongamento" -> alongamentoDetalhado()
                    "progresso" -> progressoDicas()
                    "platoo" -> platooSolucao(usuario)
                    "sono" -> sonoOtimizado()
                    "nutricao_timing" -> nutricaoTiming(usuario)
                    "mental" -> saudeMental()
                    else -> dicaComplementar(topico, usuario)
                }
                return respostaTopico
            }
        }

        if (p.contains("obrigad") || p.contains("valeu") || p.contains("thanks") || p.contains("brigad")) {
            return fechamento(usuario)
        }

        return when {
            p.contains("oi") || p.contains("ola") || p.contains("bom dia") || p.contains("boa tarde") || p.contains("boa noite") -> smartGreeting(usuario)
            p.contains("treino") && p.contains("hoje") -> treinoHoje(usuario, dia)
            p.contains("treino") && p.contains("semana") -> treinoSemana(usuario)
            p.contains("exercicio") || p.contains("exercício") -> exercicioSugerido(usuario)
            p.contains("tecnica") || p.contains("como fazer") || p.contains("como faço") -> {
                ultimoTopico = "exercicio_tecnica"
                exercicioTecnica(usuario, p)
            }
            p.contains("split") || p.contains("divisao") || p.contains("dividir") || p.contains("separar treino") -> divisaoTreino(usuario)
            p.contains("whey") || p.contains("creatina") || p.contains("suplemento") || p.contains("suplement") || p.contains("proteina po") -> {
                ultimoTopico = "suplementacao"
                suplementacao(usuario)
            }
            p.contains("cardio") || p.contains("aerobico") || p.contains("correr") || p.contains("esteira") -> {
                ultimoTopico = "cardio"
                cardioVsForca(usuario)
            }
            p.contains("aquec") || p.contains("warm up") -> {
                ultimoTopico = "aquecimento"
                aquecimentoDetalhado()
            }
            p.contains("along") || p.contains("stretch") || p.contains("flexibilidade") -> {
                ultimoTopico = "alongamento"
                alongamentoDetalhado()
            }
            p.contains("progresso") || p.contains("result") || p.contains("evolucao") || p.contains("evolução") -> {
                ultimoTopico = "progresso"
                progressoDicas()
            }
            p.contains("platoo") || p.contains("estagnacao") || p.contains("estagnação") || p.contains("parou") || p.contains("nao evoluo") -> {
                ultimoTopico = "platoo"
                platooSolucao(usuario)
            }
            p.contains("mental") || p.contains("ansiedade") || p.contains("ansioso") || p.contains("estresse") || p.contains("depressao") || p.contains("desanimad") -> {
                ultimoTopico = "mental"
                saudeMental()
            }
            p.contains("sono") || p.contains("dormir") || p.contains("noite") || p.contains("insomnio") || p.contains("cama") -> {
                ultimoTopico = "sono"
                sonoOtimizado()
            }
            p.contains("pre treino") || p.contains("pretreino") || p.contains("antes do treino") || p.contains("pos treino") || p.contains("pós treino") || p.contains("depois do treino") || p.contains("nutriç") || p.contains("nutric") || p.contains("comida") || p.contains("alimentaç") || p.contains("dieta") || p.contains("comer") || p.contains("refeicao") || p.contains("lanche") -> {
                ultimoTopico = "nutricao_timing"
                nutricaoTiming(usuario)
            }
            p.contains("descanso") || p.contains("recuperac") || p.contains("repouso") -> dicaDescanso()
            p.contains("agua") || p.contains("hidrata") || p.contains("beber") -> dicaHidratacao()
            p.contains("dor") || p.contains("lesao") || p.contains("lesão") || p.contains("machucado") -> dicaDor()
            p.contains("motivaç") || p.contains("motiva") || p.contains("desanimad") || p.contains("vontade") -> motivacao()
            p.contains("perder") && (p.contains("peso") || p.contains("gordura")) -> dicaPerdaPeso(usuario)
            p.contains("forca") || p.contains("fortalecer") || p.contains("musculaç") -> dicaForca(usuario)
            p.contains("definiç") || p.contains("definir") || p.contains("tonificar") || p.contains("secar") -> dicaDefinicao(usuario)
            p.contains("imc") || p.contains("peso ideal") -> dicaIMC(usuario)
            p.contains("intervalo") || p.contains("descanso entre") -> dicaIntervalo(usuario)
            p.contains("frequencia") || p.contains("quantas vezes") -> dicaFrequencia(usuario)
            p.contains("qual") && p.contains("melhor") -> melhorDica(usuario)
            p.contains("cansad") || p.contains("cansado") || p.contains("sem ener") || p.contains("fraco") || p.contains("sem forca") -> motivacaoCansado(usuario)
            p.contains("supino") -> exercicioTecnicaEspecifica("supino", usuario)
            p.contains("agachamento") || p.contains("agach") -> exercicioTecnicaEspecifica("agachamento", usuario)
            p.contains("deadlift") || p.contains("levantamento terra") || p.contains("terra") -> exercicioTecnicaEspecifica("deadlift", usuario)
            p.contains("remada") -> exercicioTecnicaEspecifica("remada", usuario)
            p.contains("desenvolvimento") || p.contains("ombro") -> exercicioTecnicaEspecifica("desenvolvimento", usuario)
            p.contains("rosca") -> exercicioTecnicaEspecifica("rosca", usuario)
            p.contains("triceps") -> exercicioTecnicaEspecifica("triceps", usuario)
            p.contains("prancha") || p.contains("abdomen") || p.contains("abdômen") -> exercicioTecnicaEspecifica("prancha", usuario)
            p.contains("leg") || p.contains("perna") || p.contains("quadriceps") -> exercicioTecnicaEspecifica("leg_press", usuario)
            p.contains("postura") -> dicaPostura()
            p.contains("bateria") || p.contains("energia") || p.contains("vitalidade") -> dicaEnergia(usuario)
            else -> menuPrincipal(usuario)
        }
    }

    private fun smartGreeting(usuario: Usuario): Resposta {
        val hora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val dia = obterDiaSemana()
        val musculosHoje = Exercicios.splitSemanal[dia] ?: emptyList()

        val saudacao = when {
            hora < 12 -> {
                val texto = buildString {
                    appendLine("Bom dia, ${usuario.nome}!")
                    appendLine()
                    if (musculosHoje.isNotEmpty()) {
                        appendLine("Hoje e $dia - dia de ${musculosHoje.joinToString(" e ") { it.uppercase() }}!")
                        appendLine()
                        appendLine("Dicas para comecar forte:")
                        appendLine("- Cafe da manha rico em proteina (3-4 ovos + fruta)")
                        appendLine("- Hidratacao: beba 500ml de agua ao acordar")
                        appendLine("- Aquecimento de 10min antes de ir pro treino")
                    } else {
                        appendLine("Hoje e $dia - dia de descanso!")
                        appendLine("Aproveite para:")
                        appendLine("- Alongamento leve (15-20min)")
                        appendLine("- Caminhada ao ar livre")
                        appendLine("- Preparar as refeicoes da semana")
                    }
                }
                Resposta(
                    texto,
                    if (musculosHoje.isNotEmpty()) listOf("Treino de hoje", "Dica de aquecimento", "O que comer antes?")
                    else listOf("Alongamento leve", "Dica de recuperacao", "Preparar refeicoes")
                )
            }
            hora in 12..17 -> {
                val texto = buildString {
                    appendLine("Boa tarde, ${usuario.nome}!")
                    appendLine()
                    appendLine("Hora do almoco! Algumas dicas:")
                    appendLine()
                    if (usuario.objetivo == "forca" || usuario.objetivo == "massa") {
                        appendLine("Para seu objetivo de FORCA/MASSA:")
                        appendLine("- Prato: 1/3 proteina + 1/3 carboidrato + 1/3 legumes")
                        appendLine("- Exemplo: peito de frango + arroz integral + salada")
                        appendLine("- Beba agua entre as refeicoes, nao durante")
                    } else {
                        appendLine("Para seu objetivo de ${usuario.objetivo.uppercase()}:")
                        appendLine("- Foco em proteina magra e vegetais")
                        appendLine("- Carboidratos complexos para energia")
                        appendLine("- Porcao controlada de gorduras boas (abacate, azeite)")
                    }
                }
                Resposta(
                    texto,
                    listOf("Dica de alimentacao completa", "Treino da tarde/noite", "Lanche pre-treino")
                )
            }
            else -> {
                val texto = buildString {
                    appendLine("Boa noite, ${usuario.nome}!")
                    appendLine()
                    appendLine("Hora de cuidar da recuperacao:")
                    appendLine()
                    appendLine("Rotina noturna para maximizar ganhos:")
                    appendLine("1. Jantar leve: proteina + carboidrato facil (banana, batata doce)")
                    appendLine("2. Evite telas 1h antes de dormir")
                    appendLine("3. Banho quente relaxa musculos")
                    appendLine("4. Durma ate 22h - o hormonio de crescimento picos entre 22h-2h")
                    appendLine("5. Quarto escuro e frio (18-20C)")
                }
                Resposta(
                    texto,
                    listOf("Dica de sono", "Dica de recuperacao", "Treino de amanha")
                )
            }
        }
        return saudacao
    }

    private fun treinoHoje(usuario: Usuario, dia: String): Resposta {
        val musculos = Exercicios.splitSemanal[dia] ?: emptyList()
        if (musculos.isEmpty()) {
            return Resposta(
                "Hoje e $dia - DIA DE DESCANSO!\n\n" +
                        "Seu corpo precisa disso para crescer. Musculos crescem durante o repouso.\n\n" +
                        "O que fazer hoje:\n" +
                        "- Descanso ativo: caminhada leve 20-30min\n" +
                        "- Alongamento suave 15min\n" +
                        "- Hidratacao e alimentacao de qualidade\n" +
                        "- Preparar as refeicoes da semana",
                listOf("Dica de recuperacao", "Alongamento leve", "Dica de alimentacao")
            )
        }

        val texto = buildString {
            appendLine("TREINO DE $dia - ${usuario.objetivo.uppercase()}")
            appendLine("Olá ${usuario.nome}, seu treino de hoje:")
            appendLine()

            for (m in musculos) {
                val lista = Exercicios.dados[m]?.get(usuario.objetivo) ?: emptyList()
                if (lista.isNotEmpty()) {
                    appendLine("**${
                        when(m) {
                            "peito" -> "PEITO"
                            "costas" -> "COSTAS"
                            "ombro" -> "OMBROS"
                            "braco" -> "BRACOS"
                            "perna" -> "PERNAS"
                            "abdomen" -> "ABDOMEN"
                            else -> m.uppercase()
                        }
                    }**")
                    for (ex in lista) {
                        appendLine("  ${ex.nome} - ${ex.series}x ${ex.reps}")
                        when {
                            usuario.objetivo == "forca" -> appendLine("    Descanso: ${ex.descanso}s | Foco em carga alta")
                            usuario.objetivo == "definicao" -> appendLine("    Descanso: 30-45s | Movimento controlado")
                            else -> appendLine("    Descanso: ${ex.descanso}s")
                        }
                    }
                    appendLine()
                }
            }

            appendLine("DICAS PARA HOJE:")
            if (usuario.objetivo == "forca") {
                appendLine("- Objetivo FORCA: tente aumentar peso em 2.5kg nesta semana")
                appendLine("- Descanso entre series: 2-3 minutos")
                appendLine("- Foco na fase concentrica (subir) explosiva")
            } else if (usuario.objetivo == "definicao") {
                appendLine("- Objetivo DEFINICAO: mantenha descanso curto (30-45s)")
                appendLine("- Super-series quando possivel")
                appendLine("- Cardio leve no final: 15-20min")
            } else {
                appendLine("- Mantenha boa forma em cada repeticao")
                appendLine("- Hidratacao: 200ml a cada 15min de treino")
                appendLine("- Aquecimento completo antes de comecar")
            }
        }
        return Resposta(texto, listOf("Dica de aquecimento", "Nutricao pre-treino", "Dica de hidratacao"))
    }

    private fun treinoSemana(usuario: Usuario): Resposta {
        val texto = buildString {
            appendLine("SEU TREINO DA SEMANA (${usuario.objetivo.uppercase()})")
            appendLine()
            var totalTreinos = 0
            for (dia in Exercicios.diasSemana) {
                val musculos = Exercicios.splitSemanal[dia] ?: emptyList()
                val emoji = if (musculos.isNotEmpty()) "*" else " "
                appendLine("$emoji $dia: ${musculos.joinToString(" + ") { it.uppercase() }}")
                if (musculos.isNotEmpty()) totalTreinos++
            }
            appendLine()
            appendLine("Total: $totalTreinos treinos/semana")

            when (usuario.objetivo) {
                "forca" -> {
                    appendLine("\nPROTOCOLO DE FORCA:")
                    appendLine("- Reps: 4-8 por serie")
                    appendLine("- Descanso: 2-3min entre series")
                    appendLine("- Progresao: +2.5kg por semana quando possivel")
                    appendLine("- Exercicios compostos primeiro")
                }
                "definicao" -> {
                    appendLine("\nPROTOCOLO DE DEFINICAO:")
                    appendLine("- Reps: 12-15 por serie")
                    appendLine("- Descanso: 30-60s entre series")
                    appendLine("- Super-sets e drop-sets")
                    appendLine("- Cardio HIIT no final")
                }
                "perda_de_peso" -> {
                    appendLine("\nPROTOCOLO DE PERDA DE PESO:")
                    appendLine("- Circuitos com pouco descanso")
                    appendLine("- Cardio intervalado no final")
                    appendLine("- Treinos de 45-60min maximizados")
                    appendLine("- Metabolismo elevado por ate 24h apos treino")
                }
                else -> {
                    appendLine("\nFoque na execucao correta de cada exercicio")
                    appendLine("- Progresse gradualmente na carga")
                    appendLine("- Mantenha consistencia semanal")
                }
            }
        }
        return Resposta(texto, listOf("Treino de hoje", "Dica de recuperacao", "Divisao de treino"))
    }

    private fun exercicioSugerido(usuario: Usuario): Resposta {
        val dia = obterDiaSemana()
        val musculos = Exercicios.splitSemanal[dia] ?: listOf("peito")
        val musculo = musculos.first()
        val lista = Exercicios.dados[musculo]?.get(usuario.objetivo) ?: emptyList()
        val exercicio = lista.randomOrNull()

        return if (exercicio != null) {
            val texto = buildString {
                appendLine("EXERCICIO SUGERIDO - ${musculo.uppercase()}")
                appendLine()
                appendLine("**${exercicio.nome}**")
                appendLine("${exercicio.series}x ${exercicio.reps} | Descanso: ${exercicio.descanso}s")
                appendLine()
                appendLine("TECNICA:")
                appendLine("- Posicao inicial: estaveis, core ativado")
                appendLine("- Movimento: controlado, sem balancar")
                appendLine("- Respiracao: expire na esforco, inspire na volta")
                appendLine("- Foco: sentir o musculo alvo trabalhando")
                appendLine()
                if (usuario.objetivo == "forca") {
                    appendLine("Para FORCA: use carga que falhe nas ultimas 2-3 reps")
                } else if (usuario.objetivo == "definicao") {
                    appendLine("Para DEFINICAO: 12-15 reps com peso moderado")
                }
            }
            Resposta(texto, listOf("Mais exercicios de $musculo", "Treino de hoje", "Como fazer $musculo?"))
        } else {
            Resposta(
                "Nenhum exercicio encontrado para $musculo. Consulte o treino do dia!",
                listOf("Treino de hoje", "Exercicio sugerido")
            )
        }
    }

    private fun exercicioTecnica(usuario: Usuario, pergunta: String): Resposta {
        val exercicios = mapOf(
            "supino" to """
                |**SUPINO RETO COM BARRA**
                |
                |POSICAO:
                |- Deitado no banco, pe ar no chao
                |- Pegada levemente mais larga que os ombros
                |- Escapulas juntas e retractadas
                |- Arquear levemente as costas
                |
                |MOVIMENTO:
                |- Desca a barra ate o meio do peito (toque leve)
                |- Empurre para cima em arco ate travar cotovelos
                |- Nao trave totalmente os cotovelos
                |
                |ERROS COMUNS:
                |- Bater a barra no peito (impacto)
                |- Cotovelos abrindo (lesao no ombro)
                |- Descer muito rapido (perda de controle)
                |- Pernas tremendo (falta de estabilidade)
                |
                |MUSCULOS ALVO: peito anterior, deltoides anterior, triceps
            """.trimMargin(),
            "agachamento" to """
                |**AGACHAMENTO COM BARRA**
                |
                |POSICAO:
                |- Barra nos ombros (trapezio superior)
                |- Pes na largura dos ombros ou levemente abertos
                |- Core ativado, peito para cima
                |
                |MOVIMENTO:
                |- Flexione joelhos e quadril ao mesmo tempo
                |- Desca ate coxas paralelas ao chao (ou mais)
                |- Empurre o chao com os pes para subir
                |- Mantenha joelhos alinhados com os pes
                |
                |ERROS COMUNS:
                |- Joelhos para dentro (valgo) - perigoso
                |- Coluna arredondada (use mais carga ou cores)
                |- Joelhos passando dos pes excessivamente
                |- Calcanhar saindo do chao
                |
                |MUSCULOS ALVO: quadriceps, gluteos, adutores, eretores da espinha
            """.trimMargin(),
            "deadlift" to """
                |**LEVANTAMENTO TERRA CONVENCIONAL**
                |
                |POSICAO:
                |- Pes na largura dos quadris
                |- Barra sobre o meio do pe do pe
                |- Pegada: dupla ou alternada
                |- Costas retas, peito aberto, ombros a frente da barra
                |
                |MOVIMENTO:
                |- Empurre o chao com os pes
                |- Mantenha a barra encostada na perna durante toda a subida
                |- Extensao de quadril + joelho ao mesmo tempo
                |- Trave em cima: gluteos contraidos, ombros atras
                |
                |ERROS COMUNS:
                |- Costas arredondadas (risco de hernia)
                |- Barra afastada do corpo (lesao nas costas)
                |- Puxar com os bracos (biceps tendao)
                |- Barba no joelho na descida
                |
                |MUSCULOS ALVO: posteriores, gluteos, eretores, trapézio
            """.trimMargin(),
            "remada" to """
                |**REMADA CURVADA COM BARRA**
                |
                |POSICAO:
                |- Inclinado 45-60 graus, joelhos levemente flexionados
                |- Costas retas e neutras
                |- Pegada: pronada ou neutra
                |
                |MOVIMENTO:
                |- Puxe a barra ate o abdome inferior
                |- Contrai as escapulas no topo
                |- Desça controladamente (2-3 segundos)
                |
                |ERROS COMUNS:
                |- Usar impulso do corpo (roubar rep)
                |- Coluna arredondada
                |- Pegada muito larga ou estreita
                |
                |MUSCULOS ALVO: dorsais, romboides, deltoides posteriores, biceps
            """.trimMargin(),
            "desenvolvimento" to """
                |**DESENVOLVIMENTO COM BARRA (OMBROS)**
                |
                |POSICAO:
                |- Sentado ou em pe, barra na altura dos ombros
                |- Pegada levemente mais larga que os ombros
                |- Core ativado
                |
                |MOVIMENTO:
                |- Empurre a barra para cima em arco
                |- Leve inclinacao para tras para clearance da barra
                |- Travamento leve no topo (sem bater na barra)
                |
                |ERROS COMUNS:
                |- Arquear excessivamente as costas
                |- Cotovelos abrindo (pressao no manguito rotador)
                |- Usar impulso das pernas
                |
                |MUSCULOS ALVO: deltoides (anterior, medio, posterior), triceps
            """.trimMargin(),
            "rosca" to """
                |**ROSCA DIRETA COM BARRA/HALTERES**
                |
                |POSICAO:
                |- Em pe, bracos ao lado do corpo
                |- Joelhos levemente flexionados
                |- Core ativado, costas retas
                |
                |MOVIMENTO:
                |- Flexione o cotovelo subindo o peso
                |- Cotovelos fixos ao lado do corpo
                |- Contraia o biceps no topo
                |- Desca controladamente (3s)
                |
                |ERROS COMUNS:
                |- Balancar o corpo (impulso)
                |- Cotovelos saindo do lugar
                |- Subir rapido e descer rapido
                |- Pegada muito larga ou estreita
                |
                |MUSCULOS ALVO: biceps braquial, braquial, brachioradial
            """.trimMargin(),
            "triceps" to """
                |**EXTENSAO DE TRICEPS NA POLIA**
                |
                |POSICAO:
                |- Em pe, joelhos levemente flexionados
                |- Cotovelos colados ao corpo
                |- Pegada: pronada ou neutra
                |
                |MOVIMENTO:
                |- Estenda os bracos para baixo
                |- Trave no fundo (contracao maxima)
                |- Retorne controladamente ate 90 graus
                |
                |ERROS COMUNS:
                |- Cotovelos abrando
                |- Usar impulso do corpo
                |- Pegada muito larga
                |
                |MUSCULOS ALVO: triceps (longo, lateral, medial)
            """.trimMargin(),
            "prancha" to """
                |**PRANCHA ABDOMINAL**
                |
                |POSICAO:
                |- Apoie antebraços e pontas dos pes
                |- Corpo em linha reta: cabeca, costas, quadris, pernas
                |- Core CONTRAIDO (como se fosse levar um soco)
                |
                |ERROS COMUNS:
                |- Quadris subindo (colchonete)
                |- Quadris descendo (hiperlordose)
                |- Cabeca caida (tensao no pescoco)
                |- Respiracao presa
                |
                |VARIACOES:
                |- Prancha lateral (obliquos)
                |- Prancha com elevacao de braco (estabilidade)
                |- Prancha dinamica (prancha + flexao)
                |
                |MUSCULOS ALVO: reto abdominal, transverso, obliquos, eretores
            """.trimMargin(),
            "leg_press" to """
                |**LEG PRESS 45 GRAUS**
                |
                |POSICAO:
                |- Costas coladas no encosto
                |- Pes na plataforma, na largura dos ombros
                |- Joelhos levemente flexionados no inicio
                |
                |MOVIMENTO:
                |- Desça a plataforma controladamente (até 90 graus)
                |- Empurre sem travar os joelhos
                |- Mantenha joelhos alinhados com os pes
                |
                |ERROS COMUNS:
                |- Joelhos travando no topo (lesao)
                |- Joelhos para dentro (valgo)
                |- Costas saindo do encosto
                |- Amplitude muito curta
                |
                |MUSCULOS ALVO: quadriceps, gluteos, adutores
            """.trimMargin()
        )

        val exercicioEncontrado = exercicios.entries.firstOrNull { (chave, _) ->
            p.contains(chave)
        }

        return if (exercicioEncontrado != null) {
            Resposta(
                exercicioEncontrado.value,
                listOf("Outro exercicio", "Treino de hoje", "Dica de aquecimento")
            )
        } else {
            Resposta(
                "Qual exercicio voce quer saber? Posso ensinar:\n\n" +
                        "- Supino (reto, inclinado, halteres)\n" +
                        "- Agachamento (frontal, bulgara, hack)\n" +
                        "- Deadlift (convencional, sumo, stiff)\n" +
                        "- Remada (curvada, unilateral, cavalinho)\n" +
                        "- Desenvolvimento (barra, halteres, maquina)\n" +
                        "- Rosca direta (barra, halteres, scott)\n" +
                        "- Triceps (pulley, testa, coice)\n" +
                        "- Prancha e abdominal\n" +
                        "- Leg press e exercicios de perna",
                listOf("Supino", "Agachamento", "Deadlift")
            )
        }
    }

    private fun exercicioTecnicaEspecifica(exercicio: String, usuario: Usuario): Resposta {
        val p = exercicio
        val resposta = exercicioTecnica(usuario, p)
        return resposta
    }

    private fun exercicioTecnicaAleatorio(usuario: Usuario): Resposta {
        val exercicios = listOf("supino", "agachamento", "deadlift", "remada", "desenvolvimento")
        val aleatorio = exercicios.random()
        return exercicioTecnica(usuario, aleatorio)
    }

    private fun divisaoTreino(usuario: Usuario): Resposta {
        val texto = buildString {
            appendLine("DIVISOES DE TREINO POPULARES:")
            appendLine()
            appendLine("**PUSH/PULL/LEGS (PPL)** - Intermediario")
            appendLine("Seg: Push (peito, ombro, triceps)")
            appendLine("Ter: Pull (costas, biceps)")
            appendLine("Qua: Pernas (quadriceps, posterior, panturrilha)")
            appendLine("Qui: Push | Sex: Pull | Sab: Pernas")
            appendLine()
            appendLine("**UPPER/LOWER** - Iniciante/Intermediario")
            appendLine("Seg: Upper (corpo superior)")
            appendLine("Ter: Lower (corpo inferior)")
            appendLine("Qua: Descanso")
            appendLine("Qui: Upper | Sex: Lower")
            appendLine()
            appendLine("**BRO SPLIT** - Classico")
            appendLine("Seg: Peito | Ter: Costas | Qua: Pernas")
            appendLine("Qui: Ombros | Sex: Bracos")
            appendLine()
            appendLine("**FULL BODY** - Iniciante/Condicionamento")
            appendLine("Seg/Qua/Sex: Treino completo")
            appendLine("Ter/Qui/Sab: Descanso ou cardio leve")

            appendLine()
            appendLine("Seu split atual ja e bem estruturado!")
            appendLine("Pernas 3x/semana e otimo para desenvolvimento equilibrado.")
        }
        return Resposta(texto, listOf("Treino da semana", "Treino de hoje", "Dica de recuperacao"))
    }

    private fun suplementacao(usuario: Usuario): Resposta {
        val texto = buildString {
            appendLine("GUIA DE SUPLEMENTACAO:")
            appendLine()
            appendLine("**ESSENCIAIS (comprovados):**")
            appendLine()
            appendLine("1. CREATINA MONOHIDRATADA (3-5g/dia)")
            appendLine("   - Aumenta forca e performance")
            appendLine("   - Seguro para uso prolongado")
            appendLine("   - Nao precisa de fase de carga")
            appendLine("   - Tomar todos os dias, inclusive dias de descanso")
            appendLine()
            appendLine("2. WHEY PROTEIN (se necessario)")
            appendLine("   - Use SE nao atingir proteina pela dieta")
            appendLine("   - 25-30g por porcao")
            appendLine("   - Pos-treino ou como lanche")
            appendLine("   - Concentrado vs Isolado: ambos servem")
            appendLine()
            appendLine("**OPCIONAIS (efeito moderado):**")
            appendLine()
            appendLine("3. CAFEINA (200-400mg pre-treino)")
            appendLine("   - Melhora foco e performance")
            appendLine("   - 30min antes do treino")
            appendLine("   - Evite apos as 14h")
            appendLine()
            appendLine("4. BETA-ALANINA (3-6g/dia)")
            appendLine("   - Reduz fadiga muscular")
            appendLine("   - Efeito acumulativo")

            if (usuario.objetivo == "perda_de_peso") {
                appendLine()
                appendLine("**PARA SEU OBJETIVO (PERDA DE PESO):**")
                appendLine("- L-Carnitina: 2-3g antes do cardio")
                appendLine("- Cafeina: supressao de apetite + termogenese")
                appendLine("- Glutamina: 5g pos-treino (recuperacao)")
            } else if (usuario.objetivo == "forca" || usuario.objetivo == "massa") {
                appendLine()
                appendLine("**PARA SEU OBJETIVO (FORCA/MASSA):**")
                appendLine("- Creatina: prioridade maxima")
                appendLine("- Whey: facilidade para bater meta de proteina")
                appendLine("- BCAA: opcional se proteina for alta")
            }
        }
        return Resposta(texto, listOf("Dica de alimentacao", "Treino de hoje", "Nutricao pre-treino"))
    }

    private fun suplementacaoDetalhada(): Resposta {
        val dicas = listOf(
            "Creatina: 3-5g diarios, sem necessidade de fase de carga. Pode misturar com cafe ou suco.",
            "Whey protein: ideal pos-treino (ate 2h). Concentrado e mais barato, isolado tem menos lactose.",
            "Beta-alanina: pode causar formigamento (parestesia) - e normal! O efeito vem com o uso continuo.",
            "Glutamina: 5-10g pos-treino ajudam na recuperacao, especialmente em treinos muito intensos.",
            "Omega-3: 2-3g EPA+DHA por dia. Anti-inflamatorio, ajuda na recuperacao articular."
        )
        return Resposta(
            dicas.random(),
            listOf("Suplementacao completa", "Dica de alimentacao", "Nutricao pre-treino")
        )
    }

    private fun cardioVsForca(usuario: Usuario): Resposta {
        val texto = buildString {
            appendLine("CARDIO vs FORCA - COMO EQUILIBRAR?")
            appendLine()

            if (usuario.objetivo == "perda_de_peso") {
                appendLine("PARA PERDA DE PESO (seu objetivo):")
                appendLine("- FORCA: 3-4x/semana (metabolismo basal)")
                appendLine("- CARDIO HIIT: 2-3x/semana (15-20min)")
                appendLine("- CARDIO LISS: 1-2x/semana (30-45min)")
                appendLine("- Priorize FORCA antes do cardio")
                appendLine()
                appendLine("HIIT = 30s maximo esforco + 60s descanso (repetir)")
            } else if (usuario.objetivo == "forca") {
                appendLine("PARA FORCA (seu objetivo):")
                appendLine("- FORCA: 4-5x/semana (prioridade)")
                appendLine("- CARDIO: apenas 1-2x/semana (20-30min)")
                appendLine("- Cardio HIIT leve pode ajudar na recuperacao")
                appendLine("- Evite cardio intenso no dia de perna")
                appendLine()
                appendLine("MUITO cardio prejudica ganhos de forca!")
            } else {
                appendLine("EQUILIBRIO GERAL:")
                appendLine("- FORCA: 3-4x/semana (base)")
                appendLine("- CARDIO: 2-3x/semana (complemento)")
                appendLine("- Alterne: cardio HIIT e cardio longo")
                appendLine("- Nunca no mesmo dia de perna intenso")
            }

            appendLine()
            appendLine("CARDIO IDEAL POR OBJETIVO:")
            appendLine("- Perda de peso: HIIT (queima calorias por ate 24h)")
            appendLine("- Saude cardiovascular: caminhada/bicicleta 30-45min")
            appendLine("- Resistencia: corrida, natacao, remador")
            appendLine("- Recuperacao: caminhada leve 20-30min")
        }
        return Resposta(texto, listOf("Treino de hoje", "Dica de aquecimento", "Nutricao pre-treino"))
    }

    private fun aquecimentoDetalhado(): Resposta {
        val texto = buildString {
            appendLine("AQUECIMENTO COMPLETO (10-15min):")
            appendLine()
            appendLine("**FASE 1 - ATIVACAO CARDIOVASCULAR (3-5min):**")
            appendLine("- Caminhada rapida na esteira")
            appendLine("- Bicicleta ergometrica leve")
            appendLine("- Corda (se tiver)")
            appendLine("- Bracos circulares enquanto caminha")
            appendLine()
            appendLine("**FASE 2 - MOBILIDADE ARTICULAR (3-5min):**")
            appendLine("- Rotacao de tornozelos: 10x cada")
            appendLine("- Rotacao de joelhos: 10x")
            appendLine("- Rotacao de quadril: 10x cada lado")
            appendLine("- Rotacao de ombros: 10x frente + 10x tras")
            appendLine("- Rotacao de punhos: 10x cada")
            appendLine()
            appendLine("**FASE 3 - ATIVACAO MUSCULAR (3-5min):**")
            appendLine("- 10 flexoes leves (no chao ou parede)")
            appendLine("- 10 agachamentos sem peso")
            appendLine("- 10 rotacao de tronco")
            appendLine("- 10 elevacoes laterais leves (halteres pequenos)")
            appendLine("- Prancha 20-30s")
            appendLine()
            appendLine("**DIA ESPECIFICO:**")
            appendLine("- Peito/ombro: rotacao de ombro + flexao")
            appendLine("- Costas: puxadas leves na polia + rotacao")
            appendLine("- Pernas: agachamento vazio + mobilidade de quadril")
            appendLine("- Bracos: rotacao de punho + rosca leve")
        }
        return Resposta(texto, listOf("Treino de hoje", "Alongamento", "Dica de exercicio"))
    }

    private fun alongamentoDetalhado(): Resposta {
        val texto = buildString {
            appendLine("ALONGAMENTO - PRE vs POS TREINO:")
            appendLine()
            appendLine("**ANTES DO TREINO (DINAMICO):**")
            appendLine("- NUNCA estatico (alongamento imobilizado)")
            appendLine("- Movimentos amplos e fluidos")
            appendLine("- 5-10 minutos")
            appendLine("- Exemplos: rotacoes, chutes leves, agachamentos")
            appendLine()
            appendLine("**DEPOIS DO TREINO (ESTATICO):**")
            appendLine("- Alongamento estatico: 20-30s por posicao")
            appendLine("- Respiracao profunda e constante")
            appendLine("- Nao pule nem force")
            appendLine("- 10-15 minutos")
            appendLine()
            appendLine("**SESSAO COMPLETA DE ALONGAMENTO:**")
            appendLine("1. Postura da criancinha (costas): 30s")
            appendLine("2. Alongamento de piriforme (gluteos): 30s cada lado")
            appendLine("3. Postura do 90/90 (quadril): 30s cada lado")
            appendLine("4. Alongamento de isquiotibiais: 30s cada perna")
            appendLine("5. Estocada com rotacao (psoas + torax): 30s cada lado")
            appendLine("6. Postura da cabra (abdomen + ombros): 30s")
            appendLine("7. Postura da crianca (ombros + costas): 30s")
            appendLine("8. Alongamento de peitoral (porta): 30s cada lado")
        }
        return Resposta(texto, listOf("Dica de aquecimento", "Dica de recuperacao", "Treino de hoje"))
    }

    private fun progressoDicas(): Resposta {
        val texto = buildString {
            appendLine("COMO ACOMPANHAR SEU PROGRESSO:")
            appendLine()
            appendLine("**METODOS DE AVALIACAO:**")
            appendLine()
            appendLine("1. MEDIDAS CORPORAIS (semanais):")
            appendLine("   - Peso: mesmo horario, mesma balanca")
            appendLine("   - Circunferencias: bracos, peitoral, cintura, coxa")
            appendLine("   - Fotos: frente, lado, costas (a cada 2 semanas)")
            appendLine()
            appendLine("2. PERFORMANCE NO TREINO:")
            appendLine("   - Registro de cargas e repeticoes")
            appendLine("   - Progresao: mais peso, mais reps, ou mais series")
            appendLine("   - Exemplo: supino subiu de 60kg 8x para 62.5kg 8x")
            appendLine()
            appendLine("3. INDICADORES DIARIOS:")
            appendLine("   - Energia durante o dia")
            appendLine("   - Qualidade do sono")
            appendLine("   - Apetite e recuperacao")
            appendLine("   - Como as roupas estao servindo")
            appendLine()
            appendLine("4. AVALIACAO A CADA 4 SEMANAS:")
            appendLine("   - Fotos comparativas")
            appendLine("   - Medidas corporeas")
            appendLine("   - Cargas utilizadas")
            appendLine("   - Nivel de energia geral")
            appendLine()
            appendLine("IMPORTANTE: resultados reais levam 8-12 semanas para serem notados!")
        }
        return Resposta(texto, listOf("Treino da semana", "Dica de alimentacao", "Como quebrar platoo"))
    }

    private fun platooSolucao(usuario: Usuario): Resposta {
        val texto = buildString {
            appendLine("QUEBRANDO O PLATOO DE EVOLUCAO:")
            appendLine()
            appendLine("Voce sente que nao esta evoluindo mais? Calma, e normal!")
            appendLine()
            appendLine("**ESTRATEGIAS PARA QUEBRAR O PLATOO:**")
            appendLine()
            appendLine("1. MUDANCAS NO TREINO:")
            appendLine("   - Altere ordem dos exercicios")
            appendLine("   - Troque exercicios (mesma funcao)")
            appendLine("   - Mude repeticoes (ex: 8-12 para 4-6)")
            appendLine("   - Adicione techniques: drop-sets, rest-pause, super-sets")
            appendLine()
            appendLine("2. VOLUME E INTENSIDADE:")
            appendLine("   - Aumente volume total (series x reps x carga)")
            appendLine("   - Reduza volume por 1 semana (deload)")
            appendLine("   - Volte mais forte apos o deload")
            appendLine()
            appendLine("3. NUTRICAO:")
            appendLine("   - Verifique se esta comendo o suficiente")
            appendLine("   - Aumente proteina para 2.2g/kg")
            appendLine("   - Periodize calorias (bulk/cut)")
            appendLine()
            appendLine("4. RECUPERACAO:")
            appendLine("   - Durma 7-9 horas por noite")
            appendLine("   - Reduza estresse (meditacao, caminhada)")
            appendLine("   - Considere 1 semana de descanso ativo")

            if (usuario.objetivo == "forca") {
                appendLine()
                appendLine("PARA FORCA ESPECIFICAMENTE:")
                appendLine("- Periodizacao ondulante: 4 semanas pesado, 1 leve")
                appendLine("- Exercicios auxiliares: fundamento nas fraquezas")
                appendLine("- Treino de forca maxima: 1-3 reps com 85-95% 1RM")
            }
        }
        return Resposta(texto, listOf("Treino da semana", "Dica de recuperacao", "Suplementacao"))
    }

    private fun saudeMental(): Resposta {
        val texto = buildString {
            appendLine("SAUDE MENTAL E EXERCICIO:")
            appendLine()
            appendLine("**EXERCICIO COMO MEDICINA:**")
            appendLine("- Reduz sintomas de depressao em ate 30%")
            appendLine("- Ansiedade: 30min de caminhada reduz em ate 20%")
            appendLine("- Estresse: treino regular reduz cortisol")
            appendLine("- Autoestima: melhora significativa em 4-8 semanas")
            appendLine()
            appendLine("**QUANDO ESTIVER DESANIMADO:**")
            appendLine("1. Va ao treino por apenas 15min")
            appendLine("   - Se depois de 15min quiser sair, tudo bem")
            appendLine("   - Na maioria das vezes, voce continua")
            appendLine()
            appendLine("2. Treino leve e melhor que nada")
            appendLine("   - Musculacao leve com pouca carga")
            appendLine("   - Caminhada ao ar livre")
            appendLine("   - Yoga ou alongamento")
            appendLine()
            appendLine("3. Lembre-se do POR QUE comecou")
            appendLine("   - Escreva seus motivos e releia quando desanimar")
            appendLine()
            appendLine("4. Rotina > Motivacao")
            appendLine("   - Treine nos mesmos horarios")
            appendLine("   - Faca um preparo simples (roupa pronta)")
            appendLine("   - Recompense-se apos completar treinos")
            appendLine()
            appendLine("**EXERCICIOS MELHORES PARA ANSIEDADE:**")
            appendLine("- Caminhada na natureza (20-30min)")
            appendLine("- Natacao (movimento ritmico + respiracao)")
            appendLine("- Musculacao (foco no movimento)")
            appendLine("- Yoga (respiracao + mindfulness)")
            appendLine()
            appendLine("Se os sintomas persistirem, procure ajuda profissional!")
        }
        return Resposta(texto, listOf("Me motive", "Treino de hoje", "Dica de sono"))
    }

    private fun sonoOtimizado(): Resposta {
        val texto = buildString {
            appendLine("OTIMIZACAO DO SONHO PARA GANHOS:")
            appendLine()
            appendLine("**POR QUE O SONHO E CRUCIAL:**")
            appendLine("- 70-80% do hormonio de crescimento e liberado durante o sono")
            appendLine("- Sono ruim = menos testosterona (ate 15% menos)")
            appendLine("- 1 noite ruim reduz performance em ate 10%")
            appendLine()
            appendLine("**ROTINA IDEAL:**")
            appendLine("1. Horario fixo: durma e acorde nos mesmos horarios")
            appendLine("2. 7-9 horas por noite (minimo absoluto: 6h)")
            appendLine("3. Quarto: escuro (18-20C), sem luzes")
            appendLine("4. 1h antes: sem telas (ou luz amarela)")
            appendLine("5. Evite cafeina apos as 14h")
            appendLine("6. Jantar leve: proteina + carboidrato facil")
            appendLine("7. Banho quente 1-2h antes de dormir")
            appendLine()
            appendLine("**SUPLEMENTOS PARA SONHO:**")
            appendLine("- Magnesio (200-400mg antes de dormir)")
            appendLine("- ZMA (zinco + magnesio)")
            appendLine("- Melatonina (0.5-3mg, apenas se necessario)")
            appendLine("- Chamilia ou valeriana (natural)")
            appendLine()
            appendLine("**SESSAO NOTURNA IDEAL:**")
            appendLine("- 21h: ultima refeicao leve")
            appendLine("- 21h30: preparar roupas do treino de amanha")
            appendLine("- 22h: banho quente")
            appendLine("- 22h30: respiracao profunda / meditacao leve")
            appendLine("- 23h: dormir")
        }
        return Resposta(texto, listOf("Dica de recuperacao", "Nutricao pos-treino", "Treino de amanha"))
    }

    private fun nutricaoTiming(usuario: Usuario): Resposta {
        val texto = buildString {
            appendLine("NUTRICAO PRE E POS-TREINO:")
            appendLine()
            appendLine("**ANTES DO TREINO (1-2h antes):**")
            appendLine("- Carboidrato complexo + proteina media")
            appendLine("- Exemplos:")
            appendLine("  * Aveia com whey e banana")
            appendLine("  * Arroz com frango (porcao moderada)")
            appendLine("  * Pao integral com ovo")
            appendLine("- Evite: gordura em excesso (atrasa digestao)")
            appendLine("- Evite: fibra em excesso (desconforto)")
            appendLine()
            appendLine("**IMEDIATAMENTE ANTES (15-30min):**")
            appendLine("- Carboidrato rapido se tiver energia baixa:")
            appendLine("  * Banana")
            appendLine("  * Suco de uva")
            appendLine("  * Geleia")
            appendLine()
            appendLine("**DURANTE O TREINO:**")
            appendLine("- Agua: 200ml a cada 15-20min")
            appendLine("- Treinos > 1h: BCAA ou isotonicos")
            appendLine()
            appendLine("**APOS O TREINO (ate 1h):**")
            appendLine("- Proteina rapida: 25-40g")
            appendLine("- Carboidrato: 0.5-1g por kg de peso")
            appendLine()

            val proteinaRecomendada = (usuario.peso * 1.8).toInt()
            val caloriasManutencao = (usuario.peso * 35).toInt()

            appendLine("PARA SEU PESO (${usuario.peso}kg):")
            appendLine("- Proteina pos-treino: ${proteinaRecomendada}g/dia total")
            appendLine("- Calorias manutencao: ~${caloriasManutencao} cal/dia")
            appendLine()

            if (usuario.objetivo == "perda_de_peso") {
                appendLine("PARA PERDA DE PESO:")
                appendLine("- Deficit de 300-500 cal")
                appendLine("- Mantenha proteina alta (1.8-2.2g/kg)")
                appendLine("- Pos-treino: proteina + carboidrato moderado")
            } else if (usuario.objetivo == "forca" || usuario.objetivo == "massa") {
                appendLine("PARA GANHO DE MASSA/FORCA:")
                appendLine("- Superavit de 200-300 cal")
                appendLine("- Pos-treino: proteina + carboidrato alto")
                appendLine("- Carboidrato: 4-6g por kg de peso")
            }

            appendLine()
            appendLine("**EXEMPLO DE DIA COMPLETO:**")
            appendLine("- Cafe: aveia + whey + banana")
            appendLine("- Lanche: fruta com castanhas")
            appendLine("- Pre-treino: arroz + frango (1-2h antes)")
            appendLine("- Pos-treino: whey + batata doce ou banana")
            appendLine("- Jantar: peixe + salada + arroz")
            appendLine("- Ceia: queijo cottage com frutas")
        }
        return Resposta(texto, listOf("Dica de alimentacao completa", "Treino de hoje", "Suplementacao"))
    }

    private fun motivacaoCansado(usuario: Usuario): Resposta {
        val frases = listOf(
            "Voce esta cansado, mas lembre: cada treino te torna mais forte que ontem!",
            "O cansaco e temporario, os resultados sao permanentes. Vamos la!",
            "Nao precisa de motivacao, precisa de disciplina. Coloque o tenis e va!",
            "Seu corpo esta dizendo para parar, mas sua mente pode dizer para continuar!",
            "Hoje e o dia que voce escolhe entre desculpas ou resultados!",
            "Voce ja superou dias mais dificeis que este. Va treinar e volte se sentindo vitorioso!"
        )
        return Resposta(
            "${usuario.nome}, ${frases.random()}\n\n" +
                    "DICA PRATICA:\n" +
                    "- Se estiver muito cansado, faca treino leve (50-60% da carga)\n" +
                    "- Sessao de 20min e melhor que nenhuma\n" +
                    "- As vezes o treino mais leve e o mais necessario",
            listOf("Treino de hoje", "Me motive", "Dica de recuperacao")
        )
    }

    private fun dicaPostura(): Resposta {
        return Resposta(
            "POSTURA NO TREINO E NA VIDA:\n\n" +
                    "**NO TREINO:**\n" +
                    "- Coluna neutra em todos os exercicios\n" +
                    "- Core ativado antes de cada carga\n" +
                    "- Ombros para tras e para baixo\n" +
                    "- Nao force amplitude que doer\n\n" +
                    "**NO DIA A DIA:**\n" +
                    "- Ajuste a cadeira: pes no chao, tela na altura dos olhos\n" +
                    "- A cada 30min: levante e estique\n" +
                    "- Fortaleca extensores da espinha (remada, prancha)\n" +
                    "- Alongue peitorais (porta) diariamente\n\n" +
                    "Mau postura = mais risco de lesao no treino!",
            listOf("Alongamento", "Treino de hoje", "Dica de exercicio")
        )
    }

    private fun dicaEnergia(usuario: Usuario): Resposta {
        val texto = buildString {
            appendLine("AUMENTANDO ENERGIA PARA TREINAR:")
            appendLine()
            appendLine("**CAUSAS COMUNS DE FALTA DE ENERGIA:**")
            appendLine("- Sono insuficiente (< 7h)")
            appendLine("- Desidratacao")
            appendLine("- Deficit calorico excessivo")
            appendLine("- Estresse crônico")
            appendLine("- Falta de ferro ou vitamina D")
            appendLine()
            appendLine("**SOLUCOES RAPIDAS:**")
            appendLine("1. Hidratacao: 500ml de agua ao acordar")
            appendLine("2. Cafeina: 200-300mg pre-treino (cafe preto)")
            appendLine("3. Carboidrato rapido: banana ou suco 30min antes")
            appendLine("4. Musica motivadora no trajeto")
            appendLine("5. Treino com parceiro (compromisso social)")
            appendLine()
            appendLine("**A LONGO PRAZO:**")
            appendLine("- Durma 7-9 horas por noite")
            appendLine("- Coma carbs suficientes (nao delete todos)")
            appendLine("- Suplemente vitamina D e ferro se necessario")
            appendLine("- Reduza estresse (caminhada, meditacao)")
        }
        return Resposta(texto, listOf("Nutricao pre-treino", "Dica de sono", "Me motive"))
    }

    private fun fechamento(usuario: Usuario): Resposta {
        val frases = listOf(
            "De nada, ${usuario.nome}! Estou sempre aqui. Bons treinos!",
            "Tamo junto, ${usuario.nome}! Qualquer coisa, e so chamar!",
            "Disponha! Lembre: consistencia e a chave dos resultados!",
            "Por nada! Voce esta no caminho certo. Continue assim!",
            "Estou aqui quando precisar! Treine com foco e dedicacao!"
        )
        return Resposta(
            frases.random(),
            listOf("Treino de hoje", "Me motive", "Dica de alimentacao")
        )
    }

    private fun dicaComplementar(topico: String, usuario: Usuario): Resposta {
        val dicas = mapOf(
            "exercicio_tecnica" to "Lembre-se: tecnica perfeita > carga alta. Domine o movimento antes de aumentar peso.",
            "suplementacao" to "Suplementos sao complementos, nao substitutos. Alimentacao solida primeiro!",
            "cardio" to "Cardio nem sempre e seu amigo. Muito cardio pode prejudicar ganhos de massa.",
            "aquecimento" to "10min de aquecimento podem evitar semanas de recuperacao por lesao.",
            "alongamento" to "Flexibilidade melhora com o tempo. Nao force - consistencia e mais importante que intensidade.",
            "progresso" to "Compare fotos a cada 4 semanas. O espelho mente, as fotos nao.",
            "platoo" to "Todo mundo atinge um platoo. E sinal de que precisa mudar algo no protocolo.",
            "sono" to "Durma mais. Simples assim. E onde a magica acontece.",
            "nutricao_timing" to "Timing importa, mas o TOTAL diario importa mais. Nao se estresse com minutos exatos.",
            "mental" to "Treinar quando nao quer e o que separa amadores de atletas. Va mesmo assim!"
        )
        return Resposta(
            dicas[topico] ?: "Continue focado nos seus objetivos!",
            listOf("Treino de hoje", "Me motive", "Dica de alimentacao")
        )
    }

    private fun dicaDescanso(): Resposta {
        val dicas = listOf(
            "O descanso e fundamental! Musculos crescem durante o repouso, nao no treino.",
            "Durma 7-9 horas por noite. O sono e quando o corpo libera hormonios de crescimento.",
            "Apos treinos intensos, faca um dia de descanso ativo (caminhada leve).",
            "Musculos que voce treinou precisam de 48-72h para se recuperar completamente.",
            "Descanso ativo (caminhada leve, yoga) acelera a recuperacao sem sobrecarregar.",
            "Seus musculos nao crescem no treino - crescem no sono e na alimentacao pos-treino."
        )
        return Resposta(
            dicas.random(),
            listOf("Dica de sono", "Treino de hoje", "Dica de alimentacao")
        )
    }

    private fun dicaHidratacao(): Resposta {
        val dicas = listOf(
            "Beba pelo menos 2-3 litros de agua por dia. Musculos hidratados rendem mais!",
            "Antes do treino: 500ml de agua 2h antes. Durante: 200ml a cada 20min.",
            "Apos o treino, beba agua gradualmente. Se urina for escura, voce esta desidratado.",
            "Agua com limao e uma otima opcao para treinos - ajuda na absorcao de nutrientes.",
            "Desidratacao de apenas 2% reduz performance em ate 20%!",
            "Calcule: 35ml por kg de peso corporal. Ex: 80kg = 2.8L por dia."
        )
        return Resposta(
            dicas.random(),
            listOf("Dica de alimentacao", "Treino de hoje", "Nutricao pre-treino")
        )
    }

    private fun dicaAlimentacao(): Resposta {
        val dicas = listOf(
            "Pos-treino: proteina (frango, ovo) + carboidrato (arroz, batata) em ate 1h.",
            "Antes do treino (2h): carboidrato complexo + pouca gordura para energia.",
            "Para ganho de massa: 1.6-2.2g de proteina por kg de peso corporal ao dia.",
            "Para perda de peso: mantenha deficit calorico, mas nao pule refeicoes.",
            "Frutas, legumes e vegetais devem ser metade do seu prato em todas as refeicoes.",
            "Nao delete carboidratos - eles sao combustivel para treinos intensos!"
        )
        return Resposta(
            dicas.random(),
            listOf("Nutricao pre e pos-treino", "Dica de hidratacao", "Treino de hoje")
        )
    }

    private fun dicaDor(): Resposta {
        return Resposta(
            "Dor muscular normal (DOM) vs Dor de lesao:\n\n" +
                    "NORMAL: dor que aparece 24-72h apos treino, simetrica, melhora com movimento.\n" +
                    "LESAO: dor aguda durante treino, localizada, piora com movimento.\n\n" +
                    "Para dor normal:\n" +
                    "- Continue se movendo (descanso ativo)\n" +
                    "- Banho quente e massagem leve\n" +
                    "- Hidratacao e sono adequados\n" +
                    "- Agua gelada e gelo na area\n\n" +
                    "Se a dor persistir mais de 5 dias, procure um medico!",
            listOf("Dica de recuperacao", "Alongamento leve", "Treino de hoje")
        )
    }

    private fun motivacao(): Resposta {
        val frases = listOf(
            "O unico treino ruim e o que nao foi feito. Vamos la!",
            "Cada repeticao te leva mais perto dos seus objetivos!",
            "Voce nao precisa ser perfeito, precisa ser consistente!",
            "O corpo que voce quer esta no outro lado do treino que voce esta evitando!",
            "Disciplina e fazer o que precisa ser feito, mesmo quando nao esta com vontade!",
            "Compare-se apenas com voce mesmo de ontem!",
            "Sucesso e soma de pequenos esforcos repetidos dia apos dia!",
            "A unica pessoa que voce deve tentar ser melhor que e a pessoa que voce era ontem!",
            "Dor temporaria, resultado permanente!",
            "Voce ja deu o primeiro passo - comecou. Agora continue!"
        )
        return Resposta(
            frases.random(),
            listOf("Treino de hoje", "Exercicio sugerido", "Dica de motivacao")
        )
    }

    private fun dicaPerdaPeso(usuario: Usuario): Resposta {
        val (imc, _) = DatabaseHelper.calcularImc(usuario.peso, usuario.altura)
        val caloriasManutencao = (usuario.peso * 35).toInt()
        val caloriasDeficit = caloriasManutencao - 400
        val proteinaDiaria = (usuario.peso * 2.0).toInt()
        val aguaDiaria = (usuario.peso * 0.035).toInt()

        return Resposta(
            buildString {
                appendLine("PLANO COMPLETO PARA PERDA DE PESO:")
                appendLine()
                appendLine("Seus dados:")
                appendLine("- Peso: ${usuario.peso}kg | Altura: ${usuario.altura}m")
                appendLine("- IMC: ${"%.1f".format(imc)}")
                appendLine("- Calorias manutencao: ~${caloriasManutencao} cal/dia")
                appendLine()
                appendLine("PROTOCOLO:")
                appendLine("1. DEFICIT CALORICO: ${caloriasDeficit} cal/dia (-400)")
                appendLine("2. PROTEINA ALTA: ${proteinaDiaria}g/dia (preservar massa magra)")
                appendLine("3. AGUA: ${aguaDiaria}L/dia")
                appendLine()
                appendLine("TREINO:")
                appendLine("- Forca: 3-4x/semana (aumenta metabolismo basal)")
                appendLine("- Cardio HIIT: 2-3x/semana (15-20min)")
                appendLine("- Cardio LISS: 1-2x/semana (30-45min caminhada)")
                appendLine()
                appendLine("RESULTADO ESPERADO:")
                appendLine("- 0.5-1kg por semana (saudavel)")
                appendLine("- 4-8kg por mes")
                appendLine("- Primeiros 2 semanas: perda de agua (mais rapido)")
            },
            listOf("Treino de hoje", "Nutricao pre e pos-treino", "Cardio vs Forca")
        )
    }

    private fun dicaForca(usuario: Usuario): Resposta {
        val proteinaDiaria = (usuario.peso * 2.0).toInt()
        return Resposta(
            buildString {
                appendLine("PROTOCOLO PARA GANHO DE FORCA:")
                appendLine()
                appendLine("PRINCIPIO: sobrecarga progressiva")
                appendLine()
                appendLine("1. CARGA ALTA: 4-6 reps por serie")
                appendLine("2. DESCANSO: 2-3 minutos entre series")
                appendLine("3. PROGRESAO: +2.5kg por semana (cima) ou +1 rep")
                appendLine("4. EXERCICIOS COMPOSTOS primeiro:")
                appendLine("   - Agachamento, supino, deadlift, remada, desenvolvimento")
                appendLine("5. REPETICAO: 3-5 series por exercicio")
                appendLine()
                appendLine("NUTRICAO:")
                appendLine("- Proteina: ${proteinaDiaria}g/dia")
                appendLine("- Superavit calorico: +200-300 cal")
                appendLine("- Carbs altos para energia")
                appendLine()
                appendLine("RECUPERACAO:")
                appendLine("- 48-72h entre treinos do mesmo grupo")
                appendLine("- Sono: 7-9 horas por noite")
                appendLine("- Deload a cada 4 semanas (1 semana leve)")
                appendLine()
                appendLine("Seu objetivo atual: ${usuario.objetivo.uppercase()}")
            },
            listOf("Treino da semana", "Nutricao pre e pos-treino", "Suplementacao")
        )
    }

    private fun dicaDefinicao(usuario: Usuario): Resposta {
        val caloriasManutencao = (usuario.peso * 35).toInt()
        val caloriasCorte = caloriasManutencao - 300
        return Resposta(
            buildString {
                appendLine("PROTOCOLO PARA DEFINICAO MUSCULAR:")
                appendLine()
                appendLine("PRINCIPIO: preservar massa magra + reduzir gordura")
                appendLine()
                appendLine("1. TREINO DE FORCA: manter cargas altas (nao reduza muito)")
                appendLine("2. REPS: 12-15 por serie")
                appendLine("3. DESCANSO: 30-60 segundos")
                appendLine("4. SUPER-SETS: 2 exercicios seguidos sem descanso")
                appendLine("5. DROP-SETS: reduza peso e continue ate falhar")
                appendLine()
                appendLine("CARDIO:")
                appendLine("- HIIT: 2-3x/semana (15-20min)")
                appendLine("- Pos-treino: 15-20min cardio leve")
                appendLine()
                appendLine("NUTRICAO:")
                appendLine("- Calorias: ~${caloriasCorte} cal/dia (leve deficit)")
                appendLine("- Proteina ALTA: 2.2g/kg para preservar massa")
                appendLine("- Carboidratos: ciclicos (altos no treino, baixos no descanso)")
                appendLine()
                appendLine("RESULTADO:")
                appendLine("- Perda de 0.3-0.5kg de gordura por semana")
                appendLine("- Manutencao de massa muscular")
                appendLine("- Visibilidade muscular em 6-8 semanas")
            },
            listOf("Treino da semana", "Cardio vs Forca", "Nutricao pre e pos-treino")
        )
    }

    private fun dicaIMC(usuario: Usuario): Resposta {
        val (imc, classe) = DatabaseHelper.calcularImc(usuario.peso, usuario.altura)
        val pesoIdealMin = (18.5 * usuario.altura * usuario.altura)
        val pesoIdealMax = (24.9 * usuario.altura * usuario.altura)
        val diferencaPeso = usuario.peso - pesoIdealMax

        return Resposta(
            buildString {
                appendLine("SEU INDICE DE MASSA CORPORAL:")
                appendLine()
                appendLine("IMC: ${"%.1f".format(imc)} - $classe")
                appendLine("Peso: ${usuario.peso}kg | Altura: ${usuario.altura}m")
                appendLine()
                appendLine("Peso ideal: ${"%.1f".format(pesoIdealMin)}kg - ${"%.1f".format(pesoIdealMax)}kg")
                when {
                    imc < 18.5 -> {
                        appendLine("FALTAM ${"%.1f".format(pesoIdealMin - usuario.peso)}kg para a faixa ideal")
                        appendLine()
                        appendLine("PROTOCOLO:")
                        appendLine("- Superavit calorico: +300-500 cal/dia")
                        appendLine("- Treino de forca: 4x/semana")
                        appendLine("- Proteina: 2g/kg de peso")
                        appendLine("- Consulte um nutricionista")
                    }
                    imc < 25 -> {
                        appendLine("PARABENS! Voce esta na faixa ideal!")
                        appendLine()
                        appendLine("Mantenha:")
                        appendLine("- Treino consistente")
                        appendLine("- Alimentacao equilibrada")
                        appendLine("- Hidratacao adequada")
                    }
                    imc < 30 -> {
                        appendLine("FALTAM ${"%.1f".format(diferencaPeso)}kg para a faixa ideal")
                        appendLine()
                        appendLine("PROTOCOLO:")
                        appendLine("- Deficit: -300-500 cal/dia")
                        appendLine("- Forca: 3-4x/semana")
                        appendLine("- Cardio: 2-3x/semana")
                        appendLine("- Proteina: 2g/kg para preservar massa")
                    }
                    else -> {
                        appendLine("META: ${"%.1f".format(diferencaPeso)}kg para a faixa ideal")
                        appendLine()
                        appendLine("PROTOCOLO:")
                        appendLine("- Consulte medico e nutricionista")
                        appendLine("- Deficit gradual: -500 cal/dia")
                        appendLine("- Caminhada: 30min/dia")
                        appendLine("- Forca: 2-3x/semana (cargas leves)")
                        appendLine("- Acompanhamento profissional")
                    }
                }
            },
            listOf("Treino de hoje", "Nutricao pre e pos-treino", "Plano de perda de peso")
        )
    }

    private fun dicaIntervalo(usuario: Usuario): Resposta {
        val tempoDescanso = when (usuario.objetivo) {
            "forca" -> "3-5 minutos entre series (recuperacao completa de ATP)"
            "definicao" -> "30-60 segundos entre series (manter intensidade metabolica)"
            "perda_de_peso" -> "30-45 segundos entre series (circuito para elevar cardiaco)"
            "massa" -> "60-90 segundos entre series (equilibrio entre volume e recuperacao)"
            else -> "1-2 minutos entre series"
        }

        val dicasExtras = when (usuario.objetivo) {
            "forca" -> "\nDICA: Use cronometro! Muita gente descansa menos do que acha."
            "definicao" -> "\nDICA: Use circuitos: 3-4 exercicios seguidos, depois descanse 90s."
            "perda_de_peso" -> "\nDICA: Mantenha o cardiaco elevado. Nao fique parado entre series."
            else -> ""
        }

        return Resposta(
            "TEMPO DE DESCANSO IDEAL para ${usuario.objetivo.uppercase()}:\n\n" +
                    "RECOMENDACAO: $tempoDescanso\n\n" +
                    "POR QUE IMPORTA:\n" +
                    "- Recuperacao de ATP (energia muscular)\n" +
                    "- Manter intensidade em todas as series\n" +
                    "- Evitar acumulo excessivo de acido latico" +
                    dicasExtras,
            listOf("Treino de hoje", "Exercicio sugerido", "Divisao de treino")
        )
    }

    private fun dicaFrequencia(usuario: Usuario): Resposta {
        return Resposta(
            "FREQUENCIA IDEAL DE TREINOS:\n\n" +
                    "INICIANTE: 3x/semana (full body)\n" +
                    "INTERMEDIARIO: 4-5x/semana (upper/lower ou PPL)\n" +
                    "AVANCADO: 5-6x/semana (PPL duplo)\n\n" +
                    "SEU SPLIT ATUAL: pernas 3x/semana\n" +
                    "Isso e OTIMO para desenvolvimento equilibrado!\n\n" +
                    "REGRAS:\n" +
                    "- Minimo 48h entre treinos do mesmo grupo muscular\n" +
                    "- 1-2 dias de descanso completo por semana\n" +
                    "- Deload: 1 semana leve a cada 4-6 semanas\n" +
                    "- Descanso ativo: caminhada leve nos dias de folga",
            listOf("Treino da semana", "Treino de hoje", "Dica de recuperacao")
        )
    }

    private fun melhorDica(usuario: Usuario): Resposta {
        return Resposta(
            "A MELHOR DICA PARA ${usuario.nome.uppercase()}:\n\n" +
                    "CONSISTENCIA > INTENSIDADE > PERFECOISMO\n\n" +
                    "1. Treine 3-4x por semana sem falta\n" +
                    "2. Coma bem (nao perfeito) - 80/20 regra\n" +
                    "3. Durma 7-9 horas\n" +
                    "4. Beba 2-3L de agua\n" +
                    "5. Tenha paciencia: 3-6 meses para resultados visiveis\n" +
                    "6. Registre tudo: cargas, peso, fotos\n" +
                    "7. Nao compare com outros - compare com voce de ontem\n\n" +
                    "Voce ja esta no caminho certo! Continue!",
            listOf("Treino de hoje", "Me motive", "Como acompanhar progresso")
        )
    }

    private fun obterDiaSemana(): String {
        val mapa = mapOf(
            Calendar.MONDAY to "Segunda",
            Calendar.TUESDAY to "Terca",
            Calendar.WEDNESNESS to "Quarta",
            Calendar.THURSDAY to "Quinta",
            Calendar.FRIDAY to "Sexta",
            Calendar.SATURDAY to "Sabado"
        )
        return mapa[Calendar.getInstance().get(Calendar.DAY_OF_WEEK)] ?: "Domingo"
    }

    private fun menuPrincipal(usuario: Usuario): Resposta {
        return Resposta(
            "Ola ${usuario.nome}! Posso te ajudar com:\n\n" +
                    "**TREINO:**\n" +
                    "- Treino de hoje/semana\n" +
                    "- Exercicio sugerido\n" +
                    "- Tecnica de exercicios (como fazer...)\n" +
                    "- Divisao de treino\n\n" +
                    "**NUTRICAO:**\n" +
                    "- Pre e pos-treino\n" +
                    "- Alimentacao geral\n" +
                    "- Suplementacao\n\n" +
                    "**SAUDE:**\n" +
                    "- Sono e recuperacao\n" +
                    "- Cardio vs forca\n" +
                    "- Saude mental e motivacao\n\n" +
                    "**PROGRESSO:**\n" +
                    "- Acompanhar resultados\n" +
                    "- Quebrar platoo\n" +
                    "- IMC e peso ideal\n\n" +
                    "Pergunte sobre qualquer assunto!",
            listOf("Treino de hoje", "Dica de alimentacao", "Me motive")
        )
    }
}
