package tric.tricproject.Model;

public class FinalCategory {
    private String questionTheme;
    private String answerFirstCategory;

    private String answerSecondCategory;

    public FinalCategory(String questionTheme, String answerFirstCategory, String answerSecondCategory) {
        this.questionTheme = questionTheme;
        this.answerFirstCategory = answerFirstCategory;
        this.answerSecondCategory = answerSecondCategory;
    }

    public String getQuestionTheme() {
        return questionTheme;
    }

    public void setQuestionTheme(String questionTheme) {
        this.questionTheme = questionTheme;
    }

    public String getAnswerFirstCategory() {
        return answerFirstCategory;
    }

    public void setAnswerFirstCategory(String answerFirstCategory) {
        this.answerFirstCategory = answerFirstCategory;
    }

    public String getAnswerSecondCategory() {
        return answerSecondCategory;
    }

    public void setAnswerSecondCategory(String answerSecondCategory) {
        this.answerSecondCategory = answerSecondCategory;
    }
}
