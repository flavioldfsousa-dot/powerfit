package com.powerfit.app.data

import android.content.Context
import java.util.*

object AssistenteIA {

    data class Resposta(val texto: String, val sugestoes: List<String> = emptyList())

    fun responder(pergunta: String, context: Context): Resposta {
        val p = pergunta.lowercase().trim()
        val usuario = DatabaseHelper.carregar(context)
        val dia = obterDiaSemana()

        return when {
            p.contains("treino") && p.contains("hoje") -> treinoHoje(usuario, dia)
            p.contains("treino") && p.contains("semana") -> treinoSemana(usuario)
            p.contains("exercicio") || p.contains("exercício") -> exercicioSugerido(usuario)
            p.contains("descanso") || p.contains("recuperacao") -> dicaDescanso()
            p.contains("agua") || p.contains("hidrata") || p.contains("beber") -> dicaHidratacao()
            p.contains("alimentacao") || p.contains("comida") || p.contains("comer") || p.contains("dieta") -> dicaAlimentacao()
            p.contains("aquecimento") || p.contains("alongamento") -> dicaAquecimento()
            p.contains("dor") || p.contains("lesao") || p.contains("machucado") -> dicaDor()
            p.contains("motivacao") || p.contains("motivar") || p.contains("desanimado") -> motivacao()
            p.contains("perder") && (p.contains("peso") || p.contains("gordura")) -> dicaPerdaPeso(usuario)
            p.contains("forca") || p.contains("fortalecer") || p.contains("musculacao") -> dicaForca(usuario)
            p.contains("definicao") || p.contains("definir") || p.contains("tonificar") -> dicaDefinicao(usuario)
            p.contains("imc") || p.contains("peso ideal") -> dicaIMC(usuario)
            p.contains("intervalo") || p.contains("descanso entre") -> dicaIntervalo(usuario)
            p.contains("frequencia") || p.contains("quantas vezes") -> dicaFrequencia(usuario)
            p.contains("noite") || p.contains("dormir") || p.contains("sono") -> dicaSono()
            p.contains("estresse") || p.contains("ansiedade") || p.contains("ansioso") -> dicaEstresse()
            p.contains("qual") && p.contains("melhor") -> melhorDica(usuario)
            p.contains("obrigad") || p.contains("valeu") || p.contains("thanks") -> Resposta(
                "De nada! Estou aqui para te ajudar sempre que precisar. Bons treinos!"
            )
            p.contains("ola") || p.contains("oi") || p.contains("bom dia") || p.contains("boa noite") -> saudacao(usuario)
            else -> Resposta(
                "Posso te ajudar com esses assuntos:\n\n" +
                        "- Treino de hoje/semana\n" +
                        "- Exercicios sugeridos\n" +
                        "- Descanso e recuperacao\n" +
                        "- Hidratacao e alimentacao\n" +
                        "- Aquecimento e alongamento\n" +
                        "- Motivacao\n" +
                        "- IMC e peso ideal\n" +
                        "- Frequencia de treinos\n\n" +
                        "Pergunte sobre qualquer um!",
                listOf("Treino de hoje", "Dica de alimentacao", "Me motive")
            )
        }
    }

    private fun saudacao(usuario: Usuario): Resposta {
        val hora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val saudacao = when {
            hora < 12 -> "Bom dia"
            hora < 18 -> "Boa tarde"
            else -> "Boa noite"
        }
        return Resposta(
            "$saudacao, ${usuario.nome}! Como posso te ajudar hoje?",
            listOf("Treino de hoje", "Me motive", "Dica de alimentacao")
        )
    }

    private fun treinoHoje(usuario: Usuario, dia: String): Resposta {
        val musculos = Exercicios.splitSemanal[dia] ?: emptyList()
        if (musculos.isEmpty()) {
            return Resposta(
                "Hoje e $dia e nao ha treino programado. Aproveite para descansar!",
                listOf("Dica de recuperacao", "Alongamento leve")
            )
        }
        val texto = buildString {
            appendLine("Treino de $dia - ${usuario.objetivo.uppercase()}")
            appendLine()
            for (m in musculos) {
                val lista = Exercicios.dados[m]?.get(usuario.objetivo) ?: emptyList()
                appendLine("**${m.uppercase()}**")
                for (ex in lista) {
                    appendLine("  ${ex.nome} - ${ex.series}x ${ex.reps}")
                }
                appendLine()
            }
            appendLine("Lembre-se: hidrata-se bem e faca aquecimento!")
        }
        return Resposta(texto, listOf("Dica de aquecimento", "Dica de hidratacao"))
    }

    private fun treinoSemana(usuario: Usuario): Resposta {
        val texto = buildString {
            appendLine("SEU TREINO DA SEMANA (${usuario.objetivo.uppercase()})")
            appendLine()
            for (dia in Exercicios.diasSemana) {
                val musculos = Exercicios.splitSemanal[dia] ?: emptyList()
                appendLine("$dia: ${musculos.joinToString(" e ") { it.uppercase() }}")
            }
            appendLine()
            appendLine("Pernas aparecem 3x na semana para maximo desenvolvimento!")
        }
        return Resposta(texto, listOf("Treino de hoje", "Dica de recuperacao"))
    }

    private fun exercicioSugerido(usuario: Usuario): Resposta {
        val dia = obterDiaSemana()
        val musculos = Exercicios.splitSemanal[dia] ?: listOf("peito")
        val musculo = musculos.first()
        val lista = Exercicios.dados[musculo]?.get(usuario.objetivo) ?: emptyList()
        val exercicio = lista.randomOrNull()

        return if (exercicio != null) {
            Resposta(
                "Exercicio sugerido para $musculo:\n\n" +
                        "**${exercicio.nome}**\n" +
                        "${exercicio.series}x ${exercicio.reps} | Descanso: ${exercicio.descanso}s\n\n" +
                        "Foco na tecnica e movimento controlado!",
                listOf("Mais exercicios de $musculo", "Treino de hoje")
            )
        } else {
            Resposta("Nenhum exercicio encontrado. Consulte o treino do dia!")
        }
    }

    private fun dicaDescanso(): Resposta {
        val dicas = listOf(
            "O descanso e fundamental! Musculos crescem durante o repouso, nao no treino.",
            "Durma 7-9 horas por noite. O sono e quando o corpo libera hormonios de crescimento.",
            "Apos treinos intensos, faca um dia de descanso ativo (caminhada leve).",
            "Musculos que voce treinou precisam de 48-72h para se recuperar completamente."
        )
        return Resposta(
            dicas.random(),
            listOf("Treino de hoje", "Dica de sono")
        )
    }

    private fun dicaHidratacao(): Resposta {
        val dicas = listOf(
            "Beba pelo menos 2-3 litros de agua por dia. Musculos hidratados rendem mais!",
            "Antes do treino: 500ml de agua 2h antes. Durante: 200ml a cada 20min.",
            "Apos o treino, beba agua gradualmente. Se urina for escura, voce esta desidratado.",
            "Agua com limao e uma otima opcao para treinos - ajuda na absorcao de nutrientes."
        )
        return Resposta(
            dicas.random(),
            listOf("Dica de alimentacao", "Treino de hoje")
        )
    }

    private fun dicaAlimentacao(): Resposta {
        val dicas = listOf(
            "Pos-treino: proteina (frango, ovo) + carboidrato (arroz, batata) em ate 1h.",
            "Antes do treino (2h): carboidrato complexo + pouca gordura para energia.",
            "Para ganho de massa: 1.6-2.2g de proteina por kg de peso corporal ao dia.",
            "Para perda de peso: mantenha deficit calorico, mas nao pule refeicoes.",
            "Frutas, legumes e vegetais devem ser metade do seu prato em todas as refeicoes."
        )
        return Resposta(
            dicas.random(),
            listOf("Dica de hidratacao", "Treino de hoje")
        )
    }

    private fun dicaAquecimento(): Resposta {
        return Resposta(
            "Aquecimento antes do treino e OBRIGATORIO!\n\n" +
                    "1. 5 min de caminhada/bicicleta (atividade aerobica leve)\n" +
                    "2. Rotacao de articulacoes (tornozelo, joelho, quadril, ombro, pescoco)\n" +
                    "3. 10 flexoes leves e 10 agachamentos\n" +
                    "4. Alongamento dinamico dos musculos que seraao treinados\n\n" +
                    "Isso previne lescoes e melhora a performance!",
            listOf("Treino de hoje", "Dica de alongamento")
        )
    }

    private fun dicaDor(): Resposta {
        return Resposta(
            "Dor muscular normal (DOR) vs Dor de lesao:\n\n" +
                    "NORMAL: dor que aparece 24-72h apos treino, simetrica, melhora com movimento.\n" +
                    "LESAO: dor aguda durante treino, localizada, piora com movimento.\n\n" +
                    "Para dor normal:\n" +
                    "- Continue se movendo (descanso ativo)\n" +
                    "- Banho quente e massagem leve\n" +
                    "- Hidratacao e sono adequados\n" +
                    "- Agua gelada e gelo na area\n\n" +
                    "Se a dor persistir mais de 5 dias, procure um medico!",
            listOf("Dica de recuperacao", "Alongamento leve")
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
            listOf("Treino de hoje", "Exercicio sugerido")
        )
    }

    private fun dicaPerdaPeso(usuario: Usuario): Resposta {
        val (imc, _) = DatabaseHelper.calcularImc(usuario.peso, usuario.altura)
        return Resposta(
            buildString {
                appendLine("Para perda de peso eficiente:")
                appendLine()
                appendLine("1. Deficit calorico: coma 300-500 cal a menos por dia")
                appendLine("2. Treinos de forca (como os seus): aumentam metabolismo basal")
                appendLine("3. Cardio complementar: 2-3x por semana, 30-45min")
                appendLine("4. Proteina alta: 1.8g/kg para preservar massa magra")
                appendLine("5. Agua: 35ml por kg de peso")
                appendLine()
                appendLine("Seu IMC atual: ${"%.1f".format(imc)}")
                appendLine("Meta saudavel: perder 0.5-1kg por semana")
            },
            listOf("Treino de hoje", "Dica de alimentacao")
        )
    }

    private fun dicaForca(usuario: Usuario): Resposta {
        return Resposta(
            buildString {
                appendLine("Para ganhar FORCA:")
                appendLine()
                appendLine("1. Pesos pesados: 4-8 reps por serie")
                appendLine("2. Descanso longo: 2-3 minutos entre series")
                appendLine("3. Progresao: aumente peso gradualmente")
                appendLine("4. Exercicios compostos: agachamento, supino, remada")
                appendLine("5. Repouso: 48-72h entre treinos do mesmo grupo")
                appendLine("6. Nutricao: 1.6-2.2g proteina/kg + superavit calorico")
                appendLine()
                appendLine("Seu objetivo atual: ${usuario.objetivo.uppercase()}")
            },
            listOf("Treino de hoje", "Dica de alimentacao")
        )
    }

    private fun dicaDefinicao(usuario: Usuario): Resposta {
        return Resposta(
            buildString {
                appendLine("Para DEFINIR musculos:")
                appendLine()
                appendLine("1. Reps maiores: 12-20 por serie")
                appendLine("2. Descanso curto: 30-60 segundos")
                appendLine("3. Super-series e drop-sets")
                appendLine("4. Cardio HIIT: 2-3x por semana")
                appendLine("5. Diet levemente hipocalorica")
                appendLine("6. Proteina alta para manter massa")
                appendLine()
                appendLine("Seu objetivo atual: ${usuario.objetivo.uppercase()}")
            },
            listOf("Treino de hoje", "Dica de alimentacao")
        )
    }

    private fun dicaIMC(usuario: Usuario): Resposta {
        val (imc, classe) = DatabaseHelper.calcularImc(usuario.peso, usuario.altura)
        val pesoIdealMin = (18.5 * usuario.altura * usuario.altura)
        val pesoIdealMax = (24.9 * usuario.altura * usuario.altura)

        return Resposta(
            buildString {
                appendLine("Seu IMC: ${"%.1f".format(imc)} - $classe")
                appendLine()
                appendLine("Peso ideal: ${"%.1f".format(pesoIdealMin)}kg - ${"%.1f".format(pesoIdealMax)}kg")
                appendLine("Seu peso: ${usuario.peso}kg")
                appendLine()
                when {
                    imc < 18.5 -> appendLine("Dica: Considere ganhar peso com dieta rica em nutrientes e treino de forca.")
                    imc < 25 -> appendLine("Parabens! Voce esta na faixa de peso ideal. Mantenha os treinos!")
                    imc < 30 -> appendLine("Dica: Reduza 300-500 cal/dia e aumente a atividade fisica.")
                    else -> appendLine("Dica: Consulte um nutricionista para um plano personalizado.")
                }
            },
            listOf("Treino de hoje", "Dica de alimentacao")
        )
    }

    private fun dicaIntervalo(usuario: Usuario): Resposta {
        val tempoDescanso = when (usuario.objetivo) {
            "forca" -> "2-3 minutos entre series"
            "definicao" -> "30-60 segundos entre series"
            "perda_de_peso" -> "30-45 segundos entre series (circuito)"
            else -> "1-2 minutos entre series"
        }
        return Resposta(
            "Para seu objetivo (${usuario.objetivo.uppercase()}):\n\n" +
                    "Tempo de descanso recomendado: $tempoDescanso\n\n" +
                    "Descanso adequado permite:\n" +
                    "- Recuperacao de ATP (energia muscular)\n" +
                    "- Manter intensidade em todas as series\n" +
                    "- Evitar acumulo de acido latico",
            listOf("Treino de hoje", "Exercicio sugerido")
        )
    }

    private fun dicaFrequencia(usuario: Usuario): Resposta {
        return Resposta(
            "Frequencia recomendada de treinos:\n\n" +
                    "Forca: 3-5x por semana por grupo muscular\n" +
                    "Seu split atual tem pernas 3x/semana (otimo!)\n\n" +
                    "Regras gerais:\n" +
                    "- Minimo 48h entre treinos do mesmo grupo\n" +
                    "- 3-4 treinos por semana e ideal para iniciantes\n" +
                    "- 5-6x por semana para intermediarios/avançados\n" +
                    "- 1-2 dias de descanso completo por semana",
            listOf("Treino da semana", "Treino de hoje")
        )
    }

    private fun dicaSono(): Resposta {
        return Resposta(
            "Sono e recuperacao:\n\n" +
                    "1. Durma 7-9 horas por noite\n" +
                    "2. Evite telas 1h antes de dormir\n" +
                    "3. Mantenha horario fixo (mesmo nos fins de semana)\n" +
                    "4. Quarto escuro e frio (18-20°C)\n" +
                    "5. Evite cafeina apos as 14h\n\n" +
                    "Durante o sono, o corpo libera ate 70% do hormonio de crescimento!",
            listOf("Dica de recuperacao", "Treino de hoje")
        )
    }

    private fun dicaEstresse(): Resposta {
        return Resposta(
            "Exercicio e o melhor remédio para estresse:\n\n" +
                    "1. Treino regular reduz cortisol em ate 25%\n" +
                    "2. Exercicio libera endorfinas (sensacao de bem-estar)\n" +
                    "3. Atividade fisica melhora a qualidade do sono\n" +
                    "4. Rotina de treinos da sensacao de controle\n\n" +
                    "Se sentir sobrecarregado:\n" +
                    "- Treino leve e melhor que nada\n" +
                    "- Caminhada de 20min ja ajuda muito\n" +
                    "- Yoga e alongamento reduzem ansiedade",
            listOf("Me motive", "Treino de hoje")
        )
    }

    private fun melhorDica(usuario: Usuario): Resposta {
        return Resposta(
            "Melhor conselho para ${usuario.nome}:\n\n" +
                    "CONSISTENCIA > INTENSIDADE\n\n" +
                    "1. Treine 3-4x por semana com constancia\n" +
                    "2. Nao pule refeicoes - coma bem\n" +
                    "3. Durma bem - e quando voce cresce\n" +
                    "4. Agua: minimo 2L por dia\n" +
                    "5. Paciencia: resultados levam 3-6 meses\n\n" +
                    "Voce ja esta no caminho certo usando o Zentrox Fit!",
            listOf("Treino de hoje", "Me motive")
        )
    }

    private fun obterDiaSemana(): String {
        val mapa = mapOf(
            Calendar.MONDAY to "Segunda", Calendar.TUESDAY to "Terca",
            Calendar.WEDNESDAY to "Quarta", Calendar.THURSDAY to "Quinta",
            Calendar.FRIDAY to "Sexta", Calendar.SATURDAY to "Sabado"
        )
        return mapa[Calendar.getInstance().get(Calendar.DAY_OF_WEEK)] ?: "Domingo"
    }
}
