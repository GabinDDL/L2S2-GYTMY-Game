package com.gytmy.utils;

public class Vector2 {

    private int x;
    private int y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * 
     * @param other
     * @return A new vector with the sum of the two vectors
     */
    public Vector2 add(Vector2 other) {
        return new Vector2(x + other.x, y + other.y);
    }

    /**
     * @param other
     * @return A new vector with the difference of the two vectors
     */
    public Vector2 sub(Vector2 other) {
        return new Vector2(x - other.x, y - other.y);
    }

    /**
     * @param scalar
     * @return A new vector with the same direction but multiplied by the scalar
     */
    public Vector2 mul(int scalar) {
        return new Vector2(x * scalar, y * scalar);
    }

    /**
     * Returns a new vector with the same direction but multiplied by the scalar.
     * The division is rounded to the lower integer.
     * 
     * @param scalar
     * @return A new vector with the same direction but multiplied by the scalar
     */
    public Vector2 div(int scalar) {
        if (scalar == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return new Vector2(x / scalar, y / scalar);
    }

    /**
     * @return A new vector with the opposite direction
     */
    public Vector2 neg() {
        return new Vector2(-x, -y);
    }

    /**
     * @return the Euclidean norm of this vector
     */
    public double norm() {
        return Math.sqrt((double) (x * x) + y * y);
    }

}
