/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cvt.chat.swing;

import cvt.chat.model.ModelMessage;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author datcy
 */
public interface GroupChatEvent {

    public void onGroupChatClick(MouseEvent event, ModelMessage message);
    public void onSeachBtnClick(MouseEvent event, ModelMessage message);
    public void onAddGroupBtnClick(MouseEvent event, ModelMessage message);
}
