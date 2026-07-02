package com.powerfit.app.data

object Exercicios {
    data class Exercicio(val nome: String, val series: Int, val reps: String, val descanso: Int)

    val dados = mapOf(
        "peito" to mapOf(
            "forca" to listOf(
                Exercicio("Supino Reto com Barra", 4, "8-10", 90),
                Exercicio("Supino Inclinado com Halteres", 4, "8-10", 90),
                Exercicio("Crucifixo na Maquina", 3, "10-12", 60)
            ),
            "definicao" to listOf(
                Exercicio("Supino Reto com Halteres", 4, "12-15", 45),
                Exercicio("Crossover", 4, "15-20", 30),
                Exercicio("Flexao de Bracos", 3, "ate falha", 30)
            ),
            "perda_de_peso" to listOf(
                Exercicio("Supino com Halteres (circuito)", 3, "15-20", 20),
                Exercicio("Flexao de Bracos", 3, "15-20", 20),
                Exercicio("Crossover Alto", 3, "20", 20)
            )
        ),
        "costas" to mapOf(
            "forca" to listOf(
                Exercicio("Barra Fixa (Pull-up)", 4, "6-10", 90),
                Exercicio("Remada Curvada com Barra", 4, "8-10", 90),
                Exercicio("Pulldown na Maquina", 3, "10-12", 60)
            ),
            "definicao" to listOf(
                Exercicio("Remada Unilateral com Halter", 4, "12-15", 45),
                Exercicio("Pulldown Frontal", 4, "15-20", 30),
                Exercicio("Pullover na Maquina", 3, "15-20", 30)
            ),
            "perda_de_peso" to listOf(
                Exercicio("Remada com Halter (circuito)", 3, "15-20", 20),
                Exercicio("Pulldown", 3, "20", 20),
                Exercicio("Prancha com Remada", 3, "12 cada lado", 20)
            )
        ),
        "bracos" to mapOf(
            "forca" to listOf(
                Exercicio("Rosca Direta com Barra", 4, "8-10", 60),
                Exercicio("Rosca Alternada com Halteres", 4, "8-10", 60),
                Exercicio("Rosca Martelo", 3, "10-12", 60)
            ),
            "definicao" to listOf(
                Exercicio("Rosca Direta com Barra", 4, "12-15", 30),
                Exercicio("Rosca Concentrada", 3, "15-20", 30),
                Exercicio("Rosca no Cabo", 3, "15-20", 30)
            ),
            "perda_de_peso" to listOf(
                Exercicio("Rosca com Halteres (circuito)", 3, "20", 15),
                Exercicio("Rosca Martelo", 3, "20", 15),
                Exercicio("Rosca Spider", 3, "15-20", 15)
            )
        ),
        "pernas" to mapOf(
            "forca" to listOf(
                Exercicio("Agachamento Livre", 4, "6-10", 120),
                Exercicio("Leg Press 45", 4, "8-10", 90),
                Exercicio("Stiff", 4, "8-10", 90),
                Exercicio("Cadeira Extensora", 3, "10-12", 60)
            ),
            "definicao" to listOf(
                Exercicio("Agachamento com Halteres", 4, "12-15", 45),
                Exercicio("Leg Press", 4, "15-20", 45),
                Exercicio("Cadeira Flexora", 4, "15-20", 30),
                Exercicio("Panturrilha em Pe", 4, "20-25", 30)
            ),
            "perda_de_peso" to listOf(
                Exercicio("Agachamento (circuito)", 3, "20", 20),
                Exercicio("Afundo com Halteres", 3, "15 cada perna", 20),
                Exercicio("Leg Press", 3, "20", 20),
                Exercicio("Panturrilha", 3, "25", 20)
            )
        ),
        "ombro" to mapOf(
            "forca" to listOf(
                Exercicio("Desenvolvimento com Barra", 4, "8-10", 90),
                Exercicio("Elevacao Lateral com Halteres", 4, "10-12", 60),
                Exercicio("Face Pull", 3, "12-15", 60)
            ),
            "definicao" to listOf(
                Exercicio("Desenvolvimento com Halteres", 4, "12-15", 45),
                Exercicio("Elevacao Lateral", 4, "15-20", 30),
                Exercicio("Elevacao Frontal", 3, "15-20", 30)
            ),
            "perda_de_peso" to listOf(
                Exercicio("Desenvolvimento (circuito)", 3, "15-20", 20),
                Exercicio("Elevacao Lateral", 3, "20", 20),
                Exercicio("Arnold Press", 3, "15-20", 20)
            )
        ),
        "abdomen" to mapOf(
            "forca" to listOf(
                Exercicio("Prancha", 4, "45-60s", 30),
                Exercicio("Elevacao de Pernas", 4, "12-15", 45),
                Exercicio("Russian Twist com Halter", 3, "15 cada lado", 30)
            ),
            "definicao" to listOf(
                Exercicio("Crunch na Maquina", 4, "20", 30),
                Exercicio("Elevacao de Pernas", 4, "20", 30),
                Exercicio("Prancha Lateral", 3, "30-40s cada lado", 20)
            ),
            "perda_de_peso" to listOf(
                Exercicio("Bicycle Crunch", 3, "20 cada lado", 15),
                Exercicio("Mountain Climber", 3, "30s", 15),
                Exercicio("Prancha", 3, "45s", 15)
            )
        )
    )

    val splitSemanal = mapOf(
        "Segunda" to listOf("peito", "bracos"),
        "Terca" to listOf("costas", "bracos"),
        "Quarta" to listOf("pernas", "abdomen"),
        "Quinta" to listOf("ombro", "peito"),
        "Sexta" to listOf("pernas", "costas"),
        "Sabado" to listOf("pernas", "bracos")
    )

    val diasSemana = listOf("Segunda", "Terca", "Quarta", "Quinta", "Sexta", "Sabado")
}
