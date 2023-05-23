package cvt.chat.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javaswingdev.FontAwesome;
import javaswingdev.FontAwesomeIcon;
import javaswingdev.GoogleMaterialDesignIcon;
import javaswingdev.GoogleMaterialIcon;
import javaswingdev.GradientType;
import javax.swing.JLabel;
import cvt.chat.animation.AnimationFloatingButton;
import cvt.chat.animation.AnimationScroll;
import cvt.chat.model.ModelMessage;
import cvt.chat.swing.Button;
import cvt.chat.swing.RoundPanel;
import cvt.chat.swing.TextField;
import cvt.chat.swing.scroll.ScrollBar;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import cvt.chat.swing.ChatEvent;
import cvt.chat.swing.CustomButtonEvent;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

public class SideBar extends JPanel implements CustomButtonEvent{

    private AnimationScroll animationScroll;
    private AnimationFloatingButton animationFloatingButton;
    private List<ChatEvent> events = new ArrayList<>();

    public void addChatEvent(ChatEvent event) {
        events.add(event);
    }

    public SideBar() {
        init();
        initAnimator();
    }

    private void init() {
        setOpaque(false);
        layout = new MigLayout("fill, wrap, inset 0", "[fill]", "[::5%][fill,shrink 0,::95%]");
        header = createHeader();
        body = createBody();
        
        layeredPane = createLayeredPane();
        scrollBody = createScroll();
        scrollBody.setViewportView(body);
        scrollBody.setVerticalScrollBar(new ScrollBar());
        scrollBody.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBody.getViewport().setOpaque(false);
        scrollBody.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            private int oldValues;

            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                int value = scrollBody.getVerticalScrollBar().getValue();
                int extent = scrollBody.getVerticalScrollBar().getModel().getExtent();
                if ((value + extent) >= scrollBody.getVerticalScrollBar().getMaximum() - 300) {
                    animationFloatingButton.hide();
                } else if (oldValues <= e.getValue()) {
                    if (!animationScroll.isRunning()) {
                        animationFloatingButton.show();
                    }
                }

            }
        });
        floatingButton = createFloatingButton();
        layeredPane.setLayer(floatingButton, JLayeredPane.POPUP_LAYER);
        layeredPane.add(floatingButton, "pos 100% 100%,h 40,w 40");
        layeredPane.add(scrollBody);
        setLayout(layout);
        add(header);
        add(layeredPane);
    }

    private void initAnimator() {
        animationScroll = new AnimationScroll(body);
        animationFloatingButton = new AnimationFloatingButton(layoutLayered, floatingButton);
    }

    private JPanel createBody() {
        RoundPanel panel = new RoundPanel();
        panel.setBackground(new Color(255,255,255)); //Color(46,101,186) // comment này không xóa
        panel.setLayout(new MigLayout("wrap,fillx"));
        return panel;
    }
    

    private JLayeredPane createLayeredPane() {
        JLayeredPane layer = new JLayeredPane();
        layoutLayered = new MigLayout("fill,inset 0", "[fill]", "[fill]");
        layer.setLayout(layoutLayered);
        return layer;
    }

    private Button createFloatingButton() {
        Button button = new Button();
        button.setBorder(null);
        FontAwesomeIcon icon = new FontAwesomeIcon(FontAwesome.ANGLE_DOWN, GradientType.VERTICAL, new Color(79, 79, 79, 240), new Color(248, 248, 248, 240), 35);
        button.setIcon(icon.toIcon());
        button.setRound(40);
        button.setBackground(new Color(100, 100, 100, 100));
        button.setPaintBackground(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationScroll.scrollVertical(scrollBody, scrollBody.getVerticalScrollBar().getMaximum());
            }
        });
        return button;
    }

    private JScrollPane createScroll() {
        JScrollPane scroll = new JScrollPane();
        scroll.setBorder(null);
        scroll.setViewportBorder(null);
        return scroll;
    }

    public void addChatBox(ModelMessage message) {
        int values = scrollBody.getVerticalScrollBar().getValue();
        GroupChat grChat = new GroupChat(message);
        grChat.addCustomButtonEventListener(this);
        body.add(grChat);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                body.revalidate();
                scrollBody.getVerticalScrollBar().setValue(values);
            }
        });
        body.repaint();
        body.revalidate();
        scrollBody.revalidate();
        scrollToBottom();
    }

    public void clearChatBox() {
        body.removeAll();
        body.repaint();
        body.revalidate();
    }

    private void scrollToBottom() {
        animationScroll.scrollVertical(scrollBody, scrollBody.getVerticalScrollBar().getMaximum());
    }

    private void runEventMousePressedSendButton(ActionEvent evt) {
        for (ChatEvent event : events) {
            event.mousePressedSendButton(evt);
        }
    }

    private void runEventMousePressedFileButton(ActionEvent evt) {
        for (ChatEvent event : events) {
            event.mousePressedFileButton(evt);
        }
    }

    private void runEventKeyTyped(KeyEvent evt) {
        for (ChatEvent event : events) {
            event.keyTyped(evt);
        }
    }

    public String getText() {
        return textMessage.getText();
    }

    public void setTitle(String title) {
        labelTitle.setText(title);
    }

    public String getTitle() {
        return labelTitle.getText();
    }

    public void setText(String text) {
        textMessage.setText(text);
    }

    public void textGrabFocus() {
        textMessage.grabFocus();
    }

    public void clearTextAndGrabFocus() {
        textMessage.setText("");
        textMessage.grabFocus();
    }
    
    private JPanel createHeader() {
        RoundPanel panel = new RoundPanel();
        panel.setBackground(new Color(70, 100, 136));
        panel.setLayout(new MigLayout("fill, inset 3", "[fill][fill,34!]", "[34!]"));
        GoogleMaterialIcon iconSearch = new GoogleMaterialIcon(GoogleMaterialDesignIcon.SEARCH, GradientType.VERTICAL, new Color(0, 133, 237), new Color(90, 182, 255), 20);
        GoogleMaterialIcon iconEmot = new GoogleMaterialIcon(GoogleMaterialDesignIcon.INSERT_EMOTICON, GradientType.VERTICAL, new Color(210, 210, 210), new Color(255, 255, 255), 20);
        
        Button cmdSend = new Button();
        cmdSend.setIcon(iconSearch.toIcon());
        textMessage = new TextField();
        textMessage.setHint("Tìm mọi người ...");
        textMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                revalidate();
            }

            @Override
            public void keyTyped(KeyEvent ke) {
                runEventKeyTyped(ke);
            }
        });
        cmdSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runEventMousePressedSendButton(e);
            }
        });
        JScrollPane scroll = createScroll();
        scroll.setViewportView(textMessage);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        scroll.setBackground(new Color(70, 100, 136));
        panel.add(scroll);
        panel.add(cmdSend, "height 34!");
        return panel;
    }

    private MigLayout layout;
    private MigLayout layoutLayered;
    private JLayeredPane layeredPane;
    private JPanel body;
    private TextField textMessage;
    private JScrollPane scrollBody;
    private Button floatingButton;
    private JLabel labelTitle;
    private JPanel header;

    @Override
    public void onButtonClick(ModelMessage message) {
        System.out.println("Button clicked! Callback method invoked.\tName: " + message.getName() + ", Mess: " + message.getMessage());
    }
}
