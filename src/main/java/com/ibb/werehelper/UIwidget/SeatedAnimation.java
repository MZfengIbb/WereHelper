package com.ibb.werehelper.UIwidget;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Build;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Ibb on 2016/11/18.
 */

public class SeatedAnimation extends Animation{
    private Camera camera;

    private View fromView;
    private View toView;

    private float centerX;
    private float centerY;

    private boolean forward = true;

    /**
     * Creates a 3D flip animation between two views.
     *
     * @param fromView First view in the transition.
     * @param toView   Second view in the transition.
     */
    public SeatedAnimation(View fromView, View toView) {
        this.fromView = fromView;
        this.toView = toView;

        this.fromView.setVisibility(View.VISIBLE);
        this.toView.setVisibility(View.INVISIBLE);

        setDuration(800);
        setFillAfter(false);
        setInterpolator(new AccelerateDecelerateInterpolator());
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        centerX = width / 2;
        centerY = height / 2;
        camera = new Camera();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            camera.setLocation(camera.getLocationX(),camera.getLocationY(),camera.getLocationZ()+ 50);
        }
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        // Angle around the y-axis of the rotation at the given time
        // calculated both in radians and degrees.
        final double radians = Math.PI * interpolatedTime/0.6f;
        float degrees = (float) (180.0 * radians / Math.PI);

        // Once we reach the midpoint in the animation, we need to hide the
        // source view and show the destination view. We also need to change
        // the angle by 180 degrees so that the destination does not come in
        // flipped around

        if (interpolatedTime >= 0.3f && interpolatedTime < 0.6f) {
            degrees -= 180.f;
            fromView.setVisibility(View.INVISIBLE);
            toView.setVisibility(View.VISIBLE);
        }

        if (interpolatedTime >= 0.6f) {
            degrees = 0;
        }

        if(interpolatedTime >= 0.95f) {
            toView.setVisibility(View.INVISIBLE);
        }

        if (forward)
            degrees = -degrees; //determines direction of rotation when flip begins

        final Matrix matrix = t.getMatrix();
        camera.save();
        camera.rotateY(degrees);
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
