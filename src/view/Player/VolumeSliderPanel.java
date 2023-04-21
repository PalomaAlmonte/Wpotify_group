package view.Player;

import controller.AudioController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * This class increases and decreases volume of the song using a slider on the right side of the panel.
 */
public class VolumeSliderPanel extends JPanel {

    private JSlider slider;

    public VolumeSliderPanel() {
        this.setBackground(new Color(40, 40, 40));
        slider = new JSlider();
        slider.setMinimum(0);
        slider.setMaximum(100);
        slider.setToolTipText("Volume");
        slider.setPreferredSize(new Dimension(130, 25));
        slider.setOpaque(false);
        slider.setFocusable(false);
        slider.setUI(new CustomSliderUI(slider));
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                AudioController.setMasterOutputVolume((float) slider.getValue() / 100);
            }
        });
        add(slider);
    }

    /**
     * This class is a custom UI for the slider that changes the look of the thumb and track.
     */
    private class CustomSliderUI extends BasicSliderUI {
        private final BasicStroke stroke = new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0f,
                new float[]{1f}, 0f);
        private final Dimension thumbSize = new Dimension(20, 20);
        private final Color thumbColor = new Color(255, 255, 255, 200);
        private final Color trackColor = new Color(200, 200, 200, 100);

        public CustomSliderUI(JSlider slider) {
            super(slider);
        }

        @Override
        public void paintTrack(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(stroke);
            g2d.setPaint(trackColor);
            g2d.drawLine(trackRect.x, trackRect.y + trackRect.height / 2, trackRect.x + trackRect.width,
                    trackRect.y + trackRect.height / 2);
            g2d.dispose();
        }

        @Override
        public void paintThumb(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setPaint(thumbColor);
            Shape thumbShape = createThumbShape(thumbSize.width, thumbSize.height);
            g2d.fill(thumbShape);
            g2d.draw(thumbShape);
            g2d.dispose();
        }

        @Override
        public Dimension getThumbSize() {
            return thumbSize;
        }

        @Override
        protected void calculateThumbLocation() {
            super.calculateThumbLocation();
            thumbRect.y = trackRect.y + trackRect.height / 2 - thumbRect.height / 2;
        }

        protected Shape createThumbShape(int width, int height) {
            int diameter = Math.min(width, height);
            return new Ellipse2D.Double(0, 0, diameter, diameter);
        }


    }
}




