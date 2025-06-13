/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Frontend.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 *
 * @author canti
 */
public class MyButton extends JButton{
    
    private Color base;
    private Color hover;
    private int radius = 0;
    private boolean isHover;

    public MyButton(){
        setBase(Color.WHITE);
        hover = new Color(179, 250, 160);
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                setBackground(hover);
                isHover = true;
            }

            @Override
            public void mouseExited(MouseEvent me) {
                setBackground(base);
                isHover = false;

            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (isHover) {
                    setBackground(hover);
                } else {
                    setBackground(base);
                }
            }
        });
    }

    public Color getBase() {
        return base;
    }

    public void setBase(Color base) {
        this.base = base;
        setBackground(base);
    }

    public Color getHover() {
        return hover;
    }

    public void setHover(Color hover) {
        this.hover = hover;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public boolean isIsHover() {
        return isHover;
    }

    public void setIsHover(boolean isHover) {
        this.isHover = isHover;
    }
    
    @Override
    protected void paintComponent(Graphics grphcs){
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(grphcs);
    }
}
