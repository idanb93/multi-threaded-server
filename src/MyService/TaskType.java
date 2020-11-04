package MyService;

public enum TaskType {

    IO(1){
        @Override
        public String toString() {
            return super.toString();
        }
    }, COMPUTATIONAL(2){
        @Override
        public String toString() {
            return super.toString();
        }
    }, UNKNOWN(3){
        @Override
        public String toString() {
            return super.toString();
        }
    };

    private Integer priority;

    TaskType(Integer priority) { setPriority(priority); }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer oPriority) {
        if ((oPriority >= 1) && (oPriority <= 3)) {
            this.priority = oPriority;
            System.out.println("Task priority has changed to: " + oPriority);
        }
        else {
            System.out.println("Task priority wasn't changed, Please choose a number from 1-3");
        }
    }
}

