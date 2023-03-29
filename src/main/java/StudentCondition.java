public enum StudentCondition {
    ABSENT(1),
    ILL(2),
    DOING(3);
    private int code;
    StudentCondition(int code)
    {
        this.code = code;
    }

    public int getCode()
    {
        return this.code;
    }
}
