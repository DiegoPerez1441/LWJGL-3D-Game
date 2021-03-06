package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public class Window {

    private int width, height;
    private String title;
    private long window;

    public Input input;

    public int frames;
    public long time;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void create() {
        if (!GLFW.glfwInit()) {
            System.err.println("ERROR: GLFW wasn't initialized");
            return;
        }

        input = new Input();
        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);

        if (window == 0) {
            System.err.println("ERROR: Window wasn't created");
            return;
        }

        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()); // Display to primary monitor
        GLFW.glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
        GLFW.glfwMakeContextCurrent(window); // Select window

        GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
        GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());

        GLFW.glfwShowWindow(window);
        GLFW.glfwSwapInterval(1); // Limit Window to 60fps

        time = System.currentTimeMillis(); // For fps counter

    }

    public void update() {
        GLFW.glfwPollEvents();

        // FPS Counter
        frames++;
        if (System.currentTimeMillis() > time + 1000) {
            // System.out.println(frames);
            GLFW.glfwSetWindowTitle(window, title + " | fps: " + frames); // Add fps counter to title
            time = System.currentTimeMillis();
            frames = 0; // reset after 1 second
        }
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(window);
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(window);
    }

    public void destroy() {
        input.destroy();
        GLFW.glfwWindowShouldClose(window);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

}
