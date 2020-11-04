package MyService;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;

public class TaskWrapper<V> implements RunnableFuture<V>, Comparable<TaskWrapper<V>> {

    FutureTask<V> futureTask;
    public TaskType taskType;

    public TaskWrapper(@NotNull Callable<V> callable, TaskType taskType) {
        this.futureTask = new FutureTask<>(callable);
        this.taskType = taskType;
    }
    public TaskWrapper(@NotNull Runnable runnable, V result, TaskType taskType) {
        this.futureTask = new FutureTask<>(runnable, result);
        this.taskType = taskType;
    }

    @Override
    public int compareTo(TaskWrapper otherTaskWrapper) {
        return Integer.compare(this.getPriority(), otherTaskWrapper.taskType.getPriority());
    }

    public int getPriority(){
        return this.taskType.getPriority();
    }

    @Override
    public String toString() {
        return "The type of the task is: " + taskType.name() + " And the priority of the task is: " + taskType.getPriority().toString();
    }

    @Override
    public void run() {
        this.futureTask.run();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return this.futureTask.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return this.futureTask.isCancelled();
    }

    @Override
    public boolean isDone() {
        return this.futureTask.isDone();
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        return this.futureTask.get();
    }

    @Override
    public V get(long timeout, @NotNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.futureTask.get(timeout, unit);
    }
}
