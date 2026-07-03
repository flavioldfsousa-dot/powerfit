package com.powerfit.app.data

object Exercicios {
    data class Exercicio(
        val nome: String,
        val series: Int,
        val reps: String,
        val descanso: Int,
        val aparelho: String,
        val descricao: String,
        val alternativasCasa: List<String> = emptyList(),
        val alternativasAparelho: List<String> = emptyList(),
        val muscleIcon: String = ""
    )

    val dados = mapOf(
        "peito" to mapOf(
            "forca" to listOf(
                Exercicio("Supino Reto com Barra", 4, "8-10", 90, "Barra", "Deitado no banco, empurre a barra para cima ate estender os bracos.", listOf("Flexao de braços"), listOf("Supino na maquina"), "chest"),
                Exercicio("Supino Inclinado com Halteres", 4, "8-10", 90, "Halteres", "Deitado em banco inclinado, empurre os halteres para cima.", listOf("Flexao de braços inclinada"), listOf("Supino inclinado na maquina"), "chest"),
                Exercicio("Crucifixo na Maquina", 3, "10-12", 60, "Maquina", "Sentado, junte as maos à frente do peito fechando os bracos.", listOf("Flexao de braços com amplitude ampla"), listOf("Crossover com cabos"), "chest"),
                Exercicio("Supino com Barra Livre", 4, "6-8", 120, "Barra", "Deitado no banco, empurre a barra com carga pesada para forca maxima.", listOf("Flexao de braços declinada"), listOf("Supino na maquina"), "chest"),
                Exercicio("Crossover com Cabos", 4, "10-12", 60, "Cabo", "Em pé entre dois cabos, junte as maos à frente do peito em arco.", listOf("Flexao de braços"), listOf("Crucifixo na maquina"), "chest")
            ),
            "definicao" to listOf(
                Exercicio("Supino Reto com Halteres", 4, "12-15", 45, "Halteres", "Deitado no banco, empurre os halteres controlando a descida.", listOf("Flexao de braços"), listOf("Supino na maquina"), "chest"),
                Exercicio("Crossover Alto", 4, "15-20", 30, "Cabo", "Ajuste os cabos em posicao alta e junte as maos à frente da cintura.", listOf("Flexao de braços com pegada fechada"), listOf("Crucifixo na maquina"), "chest"),
                Exercicio("Flexao de Bracos", 3, "ate falha", 30, "Peso Corporal", "Apoie as maos no chao e flexione os bracos descendo o peito.", listOf("Flexao com apoio elevado"), listOf("Supino com halteres"), "chest"),
                Exercicio("Supino Inclinado com Halteres", 4, "12-15", 45, "Halteres", "Deitado em banco inclinado, empurre os halteres com controle.", listOf("Flexao de braços inclinada"), listOf("Supino inclinado na maquina"), "chest"),
                Exercicio("Pullover com Halter", 3, "12-15", 45, "Halteres", "Deitado no banco, segure o halter acima do peito e mova para tras da cabeca.", listOf("Pullover com garrafa"), listOf("Pullover na maquina"), "chest")
            ),
            "perda_de_peso" to listOf(
                Exercicio("Supino com Halteres (circuito)", 3, "15-20", 20, "Halteres", "Execute o supino em circuito sem descanso entre series.", listOf("Flexao de braços"), listOf("Supino na maquina"), "chest"),
                Exercicio("Flexao de Bracos (circuito)", 3, "15-20", 20, "Peso Corporal", "Flexoes rapidas em circuito para queimar calorias.", listOf("Flexao com joelhos no chao"), listOf("Supino com halteres"), "chest"),
                Exercicio("Crossover Alto (circuito)", 3, "20", 20, "Cabo", "Crossover rapido em circuito, mantendo o ritmo alto.", listOf("Flexao de braços"), listOf("Crucifixo na maquina"), "chest"),
                Exercicio("Supino Reto com Halteres (circuito)", 3, "15-20", 20, "Halteres", "Supino rapido com halteres leves em circuito.", listOf("Flexao de braços"), listOf("Supino na maquina"), "chest"),
                Exercicio("Burpee com Flexao", 3, "12-15", 20, "Peso Corporal", "Agache, salte para prancha, faca flexao, salte e levante.", listOf("Mountain climber"), listOf("Supino com halteres"), "chest")
            )
        ),
        "costas" to mapOf(
            "forca" to listOf(
                Exercicio("Barra Fixa (Pull-up)", 4, "6-10", 90, "Barra", "Segure a barra e puxe o corpo para cima ate o queixo passar a barra.", listOf("Remada com garrafa pet"), listOf("Pulldown na maquina"), "back"),
                Exercicio("Remada Curvada com Barra", 4, "8-10", 90, "Barra", "Inclinado à frente, puxe a barra em direção ao abdômen.", listOf("Remada com garrafa"), listOf("Remada na maquina"), "back"),
                Exercicio("Remada Unilateral com Halter", 4, "8-10", 60, "Halteres", "Apoiado no banco, puxe o halter com um braco alternando os lados.", listOf("Remada com garrafa"), listOf("Remada na maquina"), "back"),
                Exercicio("Pulldown Frontal", 4, "8-10", 60, "Maquina", "Sentado, puxe a barra ate o peito contraindo as costas.", listOf("Remada com garrafa"), listOf("Barra fixa"), "back"),
                Exercicio("Levantamento Terra", 4, "6-8", 120, "Barra", "Em pé, levante a barra do chao mantendo as costas retas.", listOf("Agachamento com garrafa"), listOf("Levantamento terra na maquina"), "back")
            ),
            "definicao" to listOf(
                Exercicio("Remada Unilateral com Halter", 4, "12-15", 45, "Halteres", "Apoiado no banco, puxe o halter controlando o movimento.", listOf("Remada com garrafa"), listOf("Remada na maquina"), "back"),
                Exercicio("Pulldown Frontal", 4, "15-20", 30, "Maquina", "Sentado, puxe a barra ate o peito com controle.", listOf("Remada com garrafa"), listOf("Barra fixa"), "back"),
                Exercicio("Pullover na Maquina", 3, "15-20", 30, "Maquina", "Deitado no banco, empurre a barra acima da cabeca em arco.", listOf("Pullover com garrafa"), listOf("Pullover com halter"), "back"),
                Exercicio("Remada Cavalinho", 4, "12-15", 45, "Maquina", "Sentado na maquina, puxe as alças em direção ao peito.", listOf("Remada com garrafa"), listOf("Barra fixa"), "back"),
                Exercicio("Face Pull com Cabo", 3, "15-20", 30, "Cabo", "Em pé, puxe o cabo em direção ao rosto abrindo os bracos.", listOf("Remada com garrafa"), listOf("Face pull com elástico"), "back")
            ),
            "perda_de_peso" to listOf(
                Exercicio("Remada com Halter (circuito)", 3, "15-20", 20, "Halteres", "Remada rapida em circuito alternando os bracos.", listOf("Remada com garrafa"), listOf("Remada na maquina"), "back"),
                Exercicio("Pulldown (circuito)", 3, "20", 20, "Maquina", "Pulldown rapido em circuito mantendo o ritmo.", listOf("Remada com garrafa"), listOf("Barra fixa"), "back"),
                Exercicio("Prancha com Remada", 3, "12 cada lado", 20, "Halteres", "Em prancha, puxe o halter alternando os bracos.", listOf("Prancha com elevacao de perna"), listOf("Remada na maquina"), "back"),
                Exercicio("Barra Fixa (circuito)", 3, "10-15", 20, "Barra", "Barra fixa rapida em circuito.", listOf("Remada com garrafa"), listOf("Pulldown na maquina"), "back"),
                Exercicio("Superman", 3, "15-20", 20, "Peso Corporal", "Deitado de barriga para baixo, levante bracos e pernas simultaneamente.", listOf("Remada com garrafa"), listOf("Pulldown na maquina"), "back")
            )
        ),
        "bracos" to mapOf(
            "forca" to listOf(
                Exercicio("Rosca Direta com Barra", 4, "8-10", 60, "Barra", "Em pé, segure a barra e flexione os bracos levantando-a ate o peito.", listOf("Rosca com garrafa"), listOf("Rosca no cabo"), "arms"),
                Exercicio("Rosca Alternada com Halteres", 4, "8-10", 60, "Halteres", "Em pé, flexione um braco de cada vez com halteres.", listOf("Rosca com garrafa"), listOf("Rosca na maquina"), "arms"),
                Exercicio("Rosca Martelo", 3, "10-12", 60, "Halteres", "Em pé, flexione os bracos com pegada neutra (martelo).", listOf("Rosca com garrafa neutra"), listOf("Rosca martelo no cabo"), "arms"),
                Exercicio("Tríceps Testa com Barra", 4, "8-10", 60, "Barra", "Deitado no banco, flexione os bracos levando a barra à testa.", listOf("Tríceps com garrafa"), listOf("Tríceps na maquina"), "arms"),
                Exercicio("Tríceps Pulley", 4, "8-10", 60, "Cabo", "Em pé, empurre o cabo para baixo estendendo os bracos.", listOf("Tríceps com garrafa"), listOf("Tríceps testa com barra"), "arms")
            ),
            "definicao" to listOf(
                Exercicio("Rosca Direta com Barra", 4, "12-15", 30, "Barra", "Em pé, flexione os bracos controlando a descida.", listOf("Rosca com garrafa"), listOf("Rosca no cabo"), "arms"),
                Exercicio("Rosca Concentrada", 3, "15-20", 30, "Halteres", "Sentado, apoie o braco na coxa e flexione com controle.", listOf("Rosca com garrafa"), listOf("Rosca na maquina"), "arms"),
                Exercicio("Rosca no Cabo", 3, "15-20", 30, "Cabo", "Em pé, segure o cabo e flexione os bracos mantendo tensao.", listOf("Rosca com garrafa"), listOf("Rosca alternada com halteres"), "arms"),
                Exercicio("Tríceps Corda", 3, "15-20", 30, "Cabo", "Empurre a corda para baixo abrindo as maos no final.", listOf("Tríceps com garrafa"), listOf("Tríceps testa com barra"), "arms"),
                Exercicio("Rosca Martelo com Halteres", 3, "12-15", 30, "Halteres", "Flexione os bracos com pegada neutra alternando os bracos.", listOf("Rosca com garrafa"), listOf("Rosca martelo no cabo"), "arms")
            ),
            "perda_de_peso" to listOf(
                Exercicio("Rosca com Halteres (circuito)", 3, "20", 15, "Halteres", "Rosca rapida em circuito alternando os bracos.", listOf("Rosca com garrafa"), listOf("Rosca na maquina"), "arms"),
                Exercicio("Rosca Martelo (circuito)", 3, "20", 15, "Halteres", "Rosca martelo rapida em circuito.", listOf("Rosca com garrafa"), listOf("Rosca martelo no cabo"), "arms"),
                Exercicio("Tríceps no Banco", 3, "15-20", 15, "Peso Corporal", "Apoie as maos no banco e flexione os bracos empurrando o corpo.", listOf("Tríceps com garrafa"), listOf("Tríceps na maquina"), "arms"),
                Exercicio("Rosca Spider", 3, "15-20", 15, "Halteres", "Deitado de bruços no banco inclinado, flexione os bracos.", listOf("Rosca com garrafa"), listOf("Rosca no cabo"), "arms"),
                Exercicio("Mergulho no Banco (circuito)", 3, "15-20", 15, "Peso Corporal", "Apoie as maos no banco e flexione os bracos descendo o corpo.", listOf("Tríceps com garrafa"), listOf("Tríceps testa com barra"), "arms")
            )
        ),
        "pernas" to mapOf(
            "forca" to listOf(
                Exercicio("Agachamento Livre", 4, "6-10", 120, "Barra", "Com a barra nos ombros, agache ate as coxas ficarem paralelas ao chao.", listOf("Agachamento com garrafa"), listOf("Leg Press"), "legs"),
                Exercicio("Leg Press 45", 4, "8-10", 90, "Maquina", "Sentado na maquina, empurre a plataforma com as pernas.", listOf("Agachamento com garrafa"), listOf("Agachamento livre"), "legs"),
                Exercicio("Stiff", 4, "8-10", 90, "Barra", "Em pé com pernas levemente flexionadas, incline o tronco à frente mantendo as costas retas.", listOf("Stiff com garrafa"), listOf("Mesa flexora"), "legs"),
                Exercicio("Cadeira Extensora", 3, "10-12", 60, "Maquina", "Sentado, estenda as pernas contraindo os quadríceps.", listOf("Agachamento com garrafa"), listOf("Agachamento livre"), "legs"),
                Exercicio("Agachamento Frontal", 4, "6-8", 120, "Barra", "Com a barra na frente dos ombros, agache mantendo o tronco ereto.", listOf("Agachamento com garrafa"), listOf("Leg Press"), "legs")
            ),
            "definicao" to listOf(
                Exercicio("Agachamento com Halteres", 4, "12-15", 45, "Halteres", "Segure halteres aos lados e agache controlando a descida.", listOf("Agachamento com garrafa"), listOf("Leg Press"), "legs"),
                Exercicio("Leg Press", 4, "15-20", 45, "Maquina", "Sentado na maquina, empurre a plataforma com controle.", listOf("Agachamento com garrafa"), listOf("Agachamento livre"), "legs"),
                Exercicio("Cadeira Flexora", 4, "15-20", 30, "Maquina", "Sentado, flexione as pernas contraindo os isquiotibiais.", listOf("Stiff com garrafa"), listOf("Stiff com barra"), "legs"),
                Exercicio("Panturrilha em Pé", 4, "20-25", 30, "Maquina", "Em pé, suba na ponta dos pes contraindo as panturrilhas.", listOf("Panturrilha no degrau"), listOf("Panturrilha na maquina"), "legs"),
                Exercicio("Afundo com Halteres", 3, "12-15 cada perna", 45, "Halteres", "Dê um passo à frente e flexione ambos os joelhos alternando as pernas.", listOf("Afundo com garrafa"), listOf("Afundo na maquina"), "legs")
            ),
            "perda_de_peso" to listOf(
                Exercicio("Agachamento (circuito)", 3, "20", 20, "Peso Corporal", "Agachamento rapido em circuito sem peso.", listOf("Agachamento com garrafa"), listOf("Leg Press"), "legs"),
                Exercicio("Afundo com Halteres (circuito)", 3, "15 cada perna", 20, "Halteres", "Afundo rapido alternando as pernas em circuito.", listOf("Afundo com garrafa"), listOf("Afundo na maquina"), "legs"),
                Exercicio("Leg Press (circuito)", 3, "20", 20, "Maquina", "Leg press rapido em circuito.", listOf("Agachamento com garrafa"), listOf("Agachamento livre"), "legs"),
                Exercicio("Panturrilha (circuito)", 3, "25", 20, "Peso Corporal", "Panturrilha rapida em ponta dos pes.", listOf("Panturrilha no degrau"), listOf("Panturrilha na maquina"), "legs"),
                Exercicio("Burpee", 3, "15", 20, "Peso Corporal", "Agache, salte para prancha, faca flexao, salte e levante.", listOf("Mountain climber"), listOf("Agachamento com halteres"), "legs")
            )
        ),
        "ombro" to mapOf(
            "forca" to listOf(
                Exercicio("Desenvolvimento com Barra", 4, "8-10", 90, "Barra", "Sentado ou em pé, empurre a barra acima da cabeca ate estender os bracos.", listOf("Elevacao lateral com garrafa"), listOf("Desenvolvimento na maquina"), "shoulders"),
                Exercicio("Elevacao Lateral com Halteres", 4, "10-12", 60, "Halteres", "Em pé, eleve os halteres lateralmente ate a altura dos ombros.", listOf("Elevacao lateral com garrafa"), listOf("Elevacao lateral na maquina"), "shoulders"),
                Exercicio("Face Pull", 3, "12-15", 60, "Cabo", "Em pé, puxe o cabo em direção ao rosto abrindo os bracos.", listOf("Remada com garrafa"), listOf("Face pull com elástico"), "shoulders"),
                Exercicio("Elevacao Frontal com Halteres", 4, "8-10", 60, "Halteres", "Em pé, eleve os halteres à frente do corpo ate a altura dos ombros.", listOf("Elevacao frontal com garrafa"), listOf("Elevacao frontal na maquina"), "shoulders"),
                Exercicio("Desenvolvimento Arnold", 4, "8-10", 60, "Halteres", "Sentado, empurre os halteres girando as maos durante o movimento.", listOf("Elevacao lateral com garrafa"), listOf("Desenvolvimento na maquina"), "shoulders")
            ),
            "definicao" to listOf(
                Exercicio("Desenvolvimento com Halteres", 4, "12-15", 45, "Halteres", "Sentado ou em pé, empurre os halteres acima da cabeca com controle.", listOf("Elevacao lateral com garrafa"), listOf("Desenvolvimento na maquina"), "shoulders"),
                Exercicio("Elevacao Lateral", 4, "15-20", 30, "Halteres", "Em pé, eleve os halteres lateralmente controlando a descida.", listOf("Elevacao lateral com garrafa"), listOf("Elevacao lateral na maquina"), "shoulders"),
                Exercicio("Elevacao Frontal", 3, "15-20", 30, "Halteres", "Em pé, eleve os halteres à frente controlando o movimento.", listOf("Elevacao frontal com garrafa"), listOf("Elevacao frontal na maquina"), "shoulders"),
                Exercicio("Face Pull com Cabo", 3, "15-20", 30, "Cabo", "Puxe o cabo em direção ao rosto contraindo os ombros posteriores.", listOf("Remada com garrafa"), listOf("Face pull com elástico"), "shoulders"),
                Exercicio("Arnold Press com Halteres", 3, "12-15", 45, "Halteres", "Sentado, empurre os halteres girando as maos ate extensao completa.", listOf("Elevacao lateral com garrafa"), listOf("Desenvolvimento na maquina"), "shoulders")
            ),
            "perda_de_peso" to listOf(
                Exercicio("Desenvolvimento (circuito)", 3, "15-20", 20, "Halteres", "Desenvolvimento rapido em circuito.", listOf("Elevacao lateral com garrafa"), listOf("Desenvolvimento na maquina"), "shoulders"),
                Exercicio("Elevacao Lateral (circuito)", 3, "20", 20, "Halteres", "Elevacao lateral rapida em circuito.", listOf("Elevacao lateral com garrafa"), listOf("Elevacao lateral na maquina"), "shoulders"),
                Exercicio("Arnold Press (circuito)", 3, "15-20", 20, "Halteres", "Arnold press rapido em circuito.", listOf("Elevacao lateral com garrafa"), listOf("Desenvolvimento na maquina"), "shoulders"),
                Exercicio("Elevacao Frontal (circuito)", 3, "20", 20, "Halteres", "Elevacao frontal rapida em circuito.", listOf("Elevacao frontal com garrafa"), listOf("Elevacao frontal na maquina"), "shoulders"),
                Exercicio("Pike Push-up", 3, "12-15", 20, "Peso Corporal", "Flexao de bracos com o corpo em angulo invertido para ombros.", listOf("Elevacao lateral com garrafa"), listOf("Desenvolvimento na maquina"), "shoulders")
            )
        ),
        "abdomen" to mapOf(
            "forca" to listOf(
                Exercicio("Prancha", 4, "45-60s", 30, "Peso Corporal", "Mantenha o corpo reto apoiado nos antebraços e pontas dos pes.", listOf("Prancha com elevacao de perna"), listOf("Crunch na maquina"), "abs"),
                Exercicio("Elevacao de Pernas", 4, "12-15", 45, "Peso Corporal", "Deitado, eleve as pernas mantendo-as retas ate 90 graus.", listOf("Elevacao de pernas com garrafa"), listOf("Elevacao de pernas na maquina"), "abs"),
                Exercicio("Russian Twist com Halter", 3, "15 cada lado", 30, "Halteres", "Sentado com tronco inclinado, gire o torso levando o halter de um lado ao outro.", listOf("Russian twist com garrafa"), listOf("Russian twist na maquina"), "abs"),
                Exercicio("Hollow Body Hold", 4, "30-45s", 30, "Peso Corporal", "Deitado, eleve bracos e pernas mantendo a posicao curva.", listOf("Prancha com elevacao de perna"), listOf("Crunch na maquina"), "abs"),
                Exercicio("Ab Rollout com Rod", 3, "10-12", 45, "Maquina", "Ajoelhado, role a roda à frente estendendo o corpo e retorne.", listOf("Prancha com elevacao de perna"), listOf("Crunch na maquina"), "abs")
            ),
            "definicao" to listOf(
                Exercicio("Crunch na Maquina", 4, "20", 30, "Maquina", "Sentado, contraia o abdominal puxando o torso em direção aos joelhos.", listOf("Crunch no chao"), listOf("Elevacao de pernas na maquina"), "abs"),
                Exercicio("Elevacao de Pernas", 4, "20", 30, "Peso Corporal", "Deitado, eleve as pernas controlando o movimento.", listOf("Elevacao de pernas com garrafa"), listOf("Elevacao de pernas na maquina"), "abs"),
                Exercicio("Prancha Lateral", 3, "30-40s cada lado", 20, "Peso Corporal", "Apoiado em um antebraço de lado, mantenha o corpo reto.", listOf("Prancha com elevacao de perna"), listOf("Crunch na maquina"), "abs"),
                Exercicio("Crunch no Chao", 4, "15-20", 30, "Peso Corporal", "Deitado, eleve o tronco contraindo o abdominal.", listOf("Crunch com garrafa"), listOf("Crunch na maquina"), "abs"),
                Exercicio("Prancha com Elevacao de Perna", 3, "12 cada perna", 30, "Peso Corporal", "Em prancha, eleve uma perna de cada vez mantendo o corpo reto.", listOf("Prancha basica"), listOf("Crunch na maquina"), "abs")
            ),
            "perda_de_peso" to listOf(
                Exercicio("Bicycle Crunch", 3, "20 cada lado", 15, "Peso Corporal", "Deitado, alterne trazendo joelho ao cotovelo oposto em movimento de pedalar.", listOf("Mountain climber"), listOf("Crunch na maquina"), "abs"),
                Exercicio("Mountain Climber", 3, "30s", 15, "Peso Corporal", "Em prancha, alterne trazendo os joelhos ao peito rapidamente.", listOf("Bicycle crunch"), listOf("Crunch na maquina"), "abs"),
                Exercicio("Prancha (circuito)", 3, "45s", 15, "Peso Corporal", "Prancha mantida em circuito.", listOf("Prancha com elevacao de perna"), listOf("Crunch na maquina"), "abs"),
                Exercicio("Russian Twist (circuito)", 3, "20 cada lado", 15, "Peso Corporal", "Russian twist rapido sem peso em circuito.", listOf("Russian twist com garrafa"), listOf("Russian twist na maquina"), "abs"),
                Exercicio("Leg Raises no Banco", 3, "15-20", 15, "Peso Corporal", "Apoiado nas maos no banco, eleve as pernas retas.", listOf("Elevacao de pernas com garrafa"), listOf("Elevacao de pernas na maquina"), "abs")
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
