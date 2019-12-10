package com.example.habittracker

object AnalyzeProgress {
    fun start (period: Int, completedDays: Int, passedDays: Int): String{

        var overallProgress = ((completedDays.toDouble() / period) * 100).toInt()
        var currentProgress = ((completedDays.toDouble() / passedDays) * 100).toInt()
        var result = "Общий прогресс: $overallProgress%\n"
        if (completedDays + passedDays > period){
            result+= "Поздравляем с завершением твоего периода!\n\n"
            if (overallProgress < 40) result += "Очень грустные результаты, соберись с силами и попробуй ещё раз "
            if ((overallProgress >= 40) && (overallProgress < 60)) result += "Неплохо, но это для тебя не предел, ты мог(ла) и лучше"
            if ((overallProgress >= 60) && (overallProgress < 80)) result += "Достойные результаты, не останавливайся на этом"
            if ((overallProgress >= 80) && (overallProgress < 100)) result += "Отличный результат, ты можешь гордиться собой"
            if (overallProgress == 100) result += "Феноменальный результат, все дни прошли для тебя успешно, не сбавляй обороты и в каждом деле выкладывайся на все 100%"
            return result
        }
        else {
            result += "Прогресс за прошедшие дни: $currentProgress%\n\n"

            if (currentProgress < 40) result += "За прошедшие дни ты плохо поработал, попробуй приложить больше усилий, у тебя всё получится"
            if ((currentProgress >= 40) && (currentProgress < 60)) result += "Вполне нормальный результат, но тебе ещё есть куда расти"
            if ((currentProgress >= 60) && (currentProgress < 80)) result += "Очень неплохо, ещё чуть чуть и будет совсем отлично"
            if ((currentProgress >= 80) && (currentProgress <= 100)) result += "Ты молодец, отличные результаты, продолжай в том же духе"
            return result
        }

    }
}