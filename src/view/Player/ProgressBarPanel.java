package view.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProgressBarPanel extends JPanel implements Runnable {

    private JTextField passed;
    private Color background;
    private JProgressBar progressBar;
    private JTextField total;
    private int num = 0;
    private boolean isPlaying;


    public ProgressBarPanel() {

        this.isPlaying = false;
        background = new Color(40, 40, 40);
        this.setForeground(new Color(179, 179, 179));
        this.setBackground(background);

        passed = new JTextField("00:00");
        passed.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        passed.setFont(new Font("San Francisco", Font.CENTER_BASELINE, 9));
        passed.setEditable(false);
        passed.setBackground(background);
        passed.setForeground(new Color(179, 179, 179));
        this.add(passed);

        progressBar = new JProgressBar(0, 0);
        // creates an instance where the minimun is 0 and 0,used to later update
        //the time length of the music depending on the music audio time
        progressBar.setStringPainted(false);
        progressBar.setBorderPainted(true);
        progressBar.addMouseListener(new MouseAdapter() {
            @Override // this is an override of an already function built in
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                double percentage = (double) mouseX / (double) progressBar.getWidth();
                int songDuration = progressBar.getMaximum();
                int progressBarVal = (int) Math.round(percentage * songDuration);
                progressBar.setValue(progressBarVal);
                num = progressBarVal;
                passed.setText("" + progressBarVal / 60 + ":" + progressBarVal % 60);
            }
        });
        this.add(progressBar);


        total = new JTextField("00:00");
        total.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        total.setFont(new Font("Arial", Font.CENTER_BASELINE, 9));
        total.setEditable(false);
        total.setBackground(background);
        total.setForeground(new Color(179, 179, 179));
        this.add(total);

    }


    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public void refresh(int songDuration) {
        this.progressBar.setMaximum(songDuration);
        this.progressBar.setValue(0);
        this.total.setText("" + songDuration / 60 + ":" + songDuration % 60);
        this.passed.setText("00:00");
        this.num = 0;
        this.isPlaying = false;
    }



    private void iterate() {
        while (num < progressBar.getMaximum() && isPlaying == true) {
            // update progress bar on the EDT (Event Dispatch Thread)
            SwingUtilities.invokeLater(() -> {
                progressBar.setValue(num);
                passed.setText("" + progressBar.getValue() / 60 + ":" + progressBar.getValue() % 60);
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            num++;
        }
    }

    public void stop() {
        isPlaying = false;
    }


    @Override
    public void run() {
        isPlaying = true;
        iterate();
    }
}
