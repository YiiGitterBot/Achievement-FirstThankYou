package org.YiiCommunity.GitterBot.FirstThankYouAchievement;

import com.amatkivskiy.gitter.sdk.model.response.room.RoomResponse;
import com.avaje.ebean.Ebean;
import org.YiiCommunity.GitterBot.containers.Gitter;
import org.YiiCommunity.GitterBot.models.database.Achievement;
import org.YiiCommunity.GitterBot.models.database.User;

public class FirstThankYou extends org.YiiCommunity.GitterBot.api.Achievement {

    public FirstThankYou() {
        setCodeName("firstThankYou");
        setType(TYPE.ONE_TIME);

        checkAndInstall();
    }

    public void onUserChange(RoomResponse room, User user) {
        try {
            user.addAchievement(Achievement.getAchievement(getCodeName()));
            Achievement obj = Achievement.getAchievement(getCodeName());
            Gitter.sendMessage(room, obj.getChatAnnounce().replace("{username}", user.getUsername()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkAndInstall() {
        try {
            Achievement.getAchievement(getCodeName());
        } catch (Exception e) {
            Achievement obj = new Achievement();
            obj.setKey(this.getCodeName());
            obj.setChatAnnounce("First thanks to @{username}!");
            obj.setTitle("First thanks in chat");
            obj.setDescription("First thanks in chat");
            Ebean.save(obj);
        }
    }
}
