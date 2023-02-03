package com.gytmy.utils;

import java.util.Objects;

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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
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

    /**
     * @return A copy of this vector
     */
    public Vector2 copy() {
        return new Vector2(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * @param obj
     * @return true if the two vectors have the same coordinates
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vector2 other = (Vector2) obj;

        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
