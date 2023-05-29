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
import cvt.chat.swing.ChatEvent;
import cvt.chat.swing.ImageAvatar;
import java.awt.event.ContainerListener;
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
import cvt.chat.swing.GroupChatEvent;
import java.util.ArrayList;
import java.util.List;

public class GroupChat extends JComponent {
    
    private final ModelMessage message;
    private JTextPane textPane;
    private boolean active = false;

    public Color getColor() {
        if (active) {
            return new Color(174, 203, 250);
        }
        return new Color(255, 255, 255);
    }
    private List<GroupChatEvent> gEvents = new ArrayList<>();

    public void addGroupChatEvent(GroupChatEvent event) {
        gEvents.add(event);
    }

    public void runOnGroupChatClick(MouseEvent e, ModelMessage message) {
        for (GroupChatEvent event : gEvents) {
            event.onGroupChatClick(e, message);
        }
    }

    public GroupChat(ModelMessage message, boolean active) {
        this.message = message;
        this.active = active;
        init();
    }
    
    public GroupChat(ModelMessage message) {
        this.message = message;
        init();
    }
    
    private void init() {
        initBox();
    }
    
    private void initBox() {
        setLayout(new MigLayout("", "[][]", "[center]"));
        ImageAvatar avatar = new ImageAvatar();
        avatar.setBorderSize(1);
        avatar.setBorderSpace(1);
        avatar.setImage(message.getIcon());
        JTextPane text = new JTextPane();
        text.setEditorKit(new AutoWrapText());
        text.setText(message.getMessage());
        text.setBackground(getColor());
        text.setForeground(new Color(0, 0, 0));
        text.setSelectionColor(new Color(200, 200, 200, 100));
        //text.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        text.setOpaque(false);
        text.setEditable(false);
        add(avatar, "height 40,width 40");
        add(text, "span");
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, 60, 0, 0));
        g2.setPaint(new GradientPaint(0, 0, getColor(), width, 0, getColor()));
        g2.fill(area);
        g2.dispose();
        super.paintComponent(g);
    }
    
    public ModelMessage getMessage() {
        return message;
    }
}
