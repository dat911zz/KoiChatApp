package cvt.chat.component;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import cvt.chat.model.ModelMessage;
import cvt.chat.swing.AutoWrapText;
import cvt.chat.swing.CustomButtonEvent;
import cvt.chat.swing.ImageAvatar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import net.miginfocom.swing.MigLayout;

public class GroupChat extends JComponent {

    private final ModelMessage message;
    private CustomButtonEvent listener;

    public GroupChat(ModelMessage message) {
        this.message = message;
        init();
    }

    private void init() {
        initBox();
    }

    private void initBox() {
        setLayout(new MigLayout("", "[][300!]", "[center]"));
        ImageAvatar avatar = new ImageAvatar();
        avatar.setBorderSize(1);
        avatar.setBorderSpace(1);
        avatar.setImage(message.getIcon());
        JTextPane text = new JTextPane();
        text.setEditorKit(new AutoWrapText());
        text.setText(message.getMessage());
        text.setBackground(new Color(255, 255, 255));
        text.setForeground(new Color(0, 0, 0));
        text.setSelectionColor(new Color(200, 200, 200, 100));
        //text.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        text.setOpaque(false);
        text.setEditable(false);     
        add(avatar, "height 40,width 40");
        add(text, "span");
        
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                System.out.print("Chat box clicked");
                listener.onButtonClick(message);
                super.mouseClicked(e);
            }
        });
    }   
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, 60, 0, 0));
        g2.setPaint(new GradientPaint(0, 0, new Color(255,255,255), width, 0, new Color(255,255,255)));
        g2.fill(area);
        g2.dispose();
        super.paintComponent(g);
    }

    public ModelMessage getMessage() {
        return message;
    }
    public void addCustomButtonEventListener(CustomButtonEvent listener){
        this.listener = listener;
    }
}
