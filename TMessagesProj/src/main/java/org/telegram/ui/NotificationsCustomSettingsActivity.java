/*
 * This is the source code of Telegram for Android v. 5.x.x.
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Nikolai Kudashov, 2013-2018.
 */

package org.telegram.ui;

<<<<<<< HEAD
=======
import static org.telegram.messenger.AndroidUtilities.dp;
import static org.telegram.messenger.LocaleController.getString;
import static org.telegram.messenger.NotificationsController.TYPE_CHANNEL;
import static org.telegram.messenger.NotificationsController.TYPE_GROUP;
import static org.telegram.messenger.NotificationsController.TYPE_PRIVATE;
import static org.telegram.messenger.NotificationsController.TYPE_REACTIONS_MESSAGES;
import static org.telegram.messenger.NotificationsController.TYPE_REACTIONS_STORIES;
import static org.telegram.messenger.NotificationsController.TYPE_STORIES;

>>>>>>> d494ea8cb (update to 10.12.0 (4710))
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
<<<<<<< HEAD
=======
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
>>>>>>> d494ea8cb (update to 10.12.0 (4710))
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.LongSparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationsController;
import org.telegram.messenger.R;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Adapters.SearchAdapterHelper;
import org.telegram.ui.Cells.GraySectionCell;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.NotificationsCheckCell;
import org.telegram.ui.Cells.RadioColorCell;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextCell;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Cells.TextColorCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Cells.UserCell;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.BulletinFactory;
import org.telegram.ui.Components.ChatNotificationsPopupWrapper;
import org.telegram.ui.Components.EmptyTextProgressView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationsCustomSettingsActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate {

    private RecyclerListView listView;
    private ListAdapter adapter;
    private EmptyTextProgressView emptyView;
    private SearchAdapter searchAdapter;
    private AnimatorSet animatorSet;

    private boolean searchWas;
    private boolean searching;

    private final static int search_button = 0;

<<<<<<< HEAD
    private int alertRow;
    private int alertSection2Row;
    private int messageSectionRow;
    private int previewRow;
    private int messageVibrateRow;
    private int messageSoundRow;
    private int messageLedRow;
    private int messagePopupNotificationRow;
    private int messagePriorityRow;
    private int groupSection2Row;
    private int exceptionsAddRow;
    private int exceptionsStartRow;
    private int exceptionsEndRow;
    private int exceptionsSection2Row;
    private int deleteAllRow;
    private int deleteAllSectionRow;
    private int rowCount = 0;
=======
    private int settingsStart, settingsEnd;
    private int exceptionsStart, exceptionsEnd;

    private boolean showAutoExceptions = true;
    private Boolean storiesEnabled;
    private boolean storiesAuto;
>>>>>>> d494ea8cb (update to 10.12.0 (4710))

    private int currentType;
    private ArrayList<NotificationsSettingsActivity.NotificationException> exceptions;
    private HashMap<Long, NotificationsSettingsActivity.NotificationException> exceptionsDict = new HashMap<>();

    int topicId = 0;

    public NotificationsCustomSettingsActivity(int type, ArrayList<NotificationsSettingsActivity.NotificationException> notificationExceptions) {
        this(type, notificationExceptions, false);
    }

    public NotificationsCustomSettingsActivity(int type, ArrayList<NotificationsSettingsActivity.NotificationException> notificationExceptions, boolean load) {
        super();
        currentType = type;
        exceptions = notificationExceptions;
        for (int a = 0, N = exceptions.size(); a < N; a++) {
            NotificationsSettingsActivity.NotificationException exception = exceptions.get(a);
            exceptionsDict.put(exception.did, exception);
        }
        if (load) {
            loadExceptions();
        }
    }

    @Override
    public boolean onFragmentCreate() {
        updateRows(true);
        return super.onFragmentCreate();
    }

<<<<<<< HEAD
=======
    private static boolean isTop5Peer(int currentAccount, long did) {
        ArrayList<TLRPC.TL_topPeer> topPeers = new ArrayList<>(MediaDataController.getInstance(currentAccount).hints);
        Collections.sort(topPeers, Comparator.comparingDouble(a -> a.rating));
        int index = -1;
        for (int i = 0; i < topPeers.size(); ++i) {
            long did2 = DialogObject.getPeerDialogId(topPeers.get(i).peer);
            if (did2 == did) {
                index = i;
            }
        }
        return index >= 0 && index >= topPeers.size() - 5;
    }

    public static boolean areStoriesNotMuted(int currentAccount, long did) {
        SharedPreferences prefs = MessagesController.getNotificationsSettings(currentAccount);
        if (prefs.contains("stories_" + did)) {
            return prefs.getBoolean("stories_" + did, true);
        }
        if (prefs.contains("EnableAllStories")) {
            return prefs.getBoolean("EnableAllStories", true);
        }
        return isTop5Peer(currentAccount, did);
    }

    private void deleteException(NotificationsSettingsActivity.NotificationException exception, View view, int position) {
        final String key = NotificationsController.getSharedPrefKey(exception.did, 0);
        final SharedPreferences prefs = getNotificationsSettings();
        prefs.edit().remove("stories_" + key).commit();
        if (autoExceptions != null) {
            autoExceptions.remove(exception);
        }
        if (exceptions != null) {
            exceptions.remove(exception);
        }
        if (isTop5Peer(currentAccount, exception.did)) {
            exception.auto = true;
            exception.notify = 0;
            autoExceptions.add(exception);
        }
        if (view instanceof UserCell) {
            ((UserCell) view).setException(exception, null, ((UserCell) view).needDivider);
        }
        getNotificationsController().updateServerNotificationsSettings(exception.did, 0, false);
        updateRows(true);
    }

    private void updateMute(NotificationsSettingsActivity.NotificationException exception, View view, int position, boolean isNew, boolean mute) {
        final String key = NotificationsController.getSharedPrefKey(exception.did, 0);
        final SharedPreferences prefs = getNotificationsSettings();
        final SharedPreferences.Editor edit = prefs.edit();

        boolean isTopPeer = isTop5Peer(currentAccount, exception.did);
        exception.notify = mute ? Integer.MAX_VALUE : 0;
        if (exception.auto) {
            exception.auto = false;
            edit.putBoolean("stories_" + key, !mute).commit();
            if (autoExceptions != null) {
                autoExceptions.remove(exception);
            }
            if (exceptions == null) {
                exceptions = new ArrayList<>();
            }
            exceptions.add(0, exception);
            // autoExceptions -> exceptions
            // auto = false
            // (un)mute
        } else if (isTopPeer) {
            edit.putBoolean("stories_" + key, !mute).commit();
        } else if (mute ? (storiesEnabled == null || !storiesEnabled) : (storiesEnabled != null && storiesEnabled)) {
            deleteException(exception, view, position);
            return;
        } else {
            edit.putBoolean("stories_" + key, !mute).commit();
        }

        if (view instanceof UserCell) {
            ((UserCell) view).setException(exception, null, ((UserCell) view).needDivider);
        }
        getNotificationsController().updateServerNotificationsSettings(exception.did, 0, false);
        updateRows(true);
    }

    private int getLedColor() {
        int color = 0xff0000ff;
        switch (currentType) {
            case TYPE_PRIVATE: color = getNotificationsSettings().getInt("MessagesLed", color); break;
            case TYPE_GROUP:   color = getNotificationsSettings().getInt("GroupLed", color); break;
            case TYPE_STORIES: color = getNotificationsSettings().getInt("StoriesLed", color); break;
            case TYPE_CHANNEL: color = getNotificationsSettings().getInt("ChannelLed", color); break;
            case TYPE_REACTIONS_MESSAGES:
            case TYPE_REACTIONS_STORIES: color = getNotificationsSettings().getInt("ReactionsLed", color); break;
        }
        for (int a = 0; a < 9; a++) {
            if (TextColorCell.colorsToSave[a] == color) {
                color = TextColorCell.colors[a];
                break;
            }
        }
        return color;
    }

    private String getPopupOption() {
        int option = 0;
        switch (currentType) {
            case TYPE_PRIVATE: option = getNotificationsSettings().getInt("popupAll", 0); break;
            case TYPE_GROUP:   option = getNotificationsSettings().getInt("popupGroup", 0); break;
            case TYPE_CHANNEL: option = getNotificationsSettings().getInt("popupChannel", 0); break;
        }
        return getString(popupOptions[Utilities.clamp(option, popupOptions.length - 1, 0)]);
    }

    private String getSound() {
        final SharedPreferences prefs = getNotificationsSettings();
        String value = getString("SoundDefault", R.string.SoundDefault);
        long documentId;
        switch (currentType) {
            case TYPE_PRIVATE:
                value = prefs.getString("GlobalSound", value);
                documentId = prefs.getLong("GlobalSoundDocId", 0);
                break;
            case TYPE_GROUP:
                value = prefs.getString("GroupSound", value);
                documentId = prefs.getLong("GroupSoundDocId", 0);
                break;
            case TYPE_REACTIONS_MESSAGES:
            case TYPE_REACTIONS_STORIES:
                value = prefs.getString("ReactionSound", value);
                documentId = prefs.getLong("ReactionSoundDocId", 0);
                break;
            case TYPE_STORIES:
                value = prefs.getString("StoriesSound", value);
                documentId = prefs.getLong("StoriesSoundDocId", 0);
                break;
            case TYPE_CHANNEL:
            default:
                value = prefs.getString("ChannelSound", value);
                documentId = prefs.getLong("ChannelDocId", 0);
        }
        if (documentId != 0) {
            TLRPC.Document document = getMediaDataController().ringtoneDataStore.getDocument(documentId);
            if (document == null) {
                return getString("CustomSound", R.string.CustomSound);
            } else {
                return NotificationsSoundActivity.trimTitle(document, FileLoader.getDocumentFileName(document));
            }
        } else if (value.equals("NoSound")) {
            return getString("NoSound", R.string.NoSound);
        } else if (value.equals("Default")) {
            return getString("SoundDefault", R.string.SoundDefault);
        }
        return value;
    }

    private String getPriorityOption() {
        int option = 1;
        switch (currentType) {
            case TYPE_PRIVATE: option = getNotificationsSettings().getInt("priority_messages", 1); break;
            case TYPE_GROUP:   option = getNotificationsSettings().getInt("priority_group", 1); break;
            case TYPE_STORIES: option = getNotificationsSettings().getInt("priority_stories", 1); break;
            case TYPE_CHANNEL: option = getNotificationsSettings().getInt("priority_channel", 1); break;
            case TYPE_REACTIONS_MESSAGES:
            case TYPE_REACTIONS_STORIES: option = getNotificationsSettings().getInt("priority_react", 1); break;
        }
        return getString(priorityOptions[Utilities.clamp(option, priorityOptions.length - 1, 0)]);
    }

>>>>>>> d494ea8cb (update to 10.12.0 (4710))
    @Override
    public View createView(Context context) {
        searching = false;
        searchWas = false;

        actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        actionBar.setAllowOverlayTitle(true);
        if (currentType == -1) {
            actionBar.setTitle(getString("NotificationsExceptions", R.string.NotificationsExceptions));
        } else {
            actionBar.setTitle(getString("Notifications", R.string.Notifications));
        }
        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                if (id == -1) {
                    finishFragment();
                }
            }
        });
        if (exceptions != null && !exceptions.isEmpty()) {
            ActionBarMenu menu = actionBar.createMenu();
            ActionBarMenuItem searchItem = menu.addItem(search_button, R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new ActionBarMenuItem.ActionBarMenuItemSearchListener() {
                @Override
                public void onSearchExpand() {
                    searching = true;
                    emptyView.setShowAtCenter(true);
                }

                @Override
                public void onSearchCollapse() {
                    searchAdapter.searchDialogs(null);
                    searching = false;
                    searchWas = false;
                    emptyView.setText(getString("NoExceptions", R.string.NoExceptions));
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    listView.setFastScrollVisible(true);
                    listView.setVerticalScrollBarEnabled(false);
                    emptyView.setShowAtCenter(false);
                }

                @Override
                public void onTextChanged(EditText editText) {
                    if (searchAdapter == null) {
                        return;
                    }
                    String text = editText.getText().toString();
                    if (text.length() != 0) {
                        searchWas = true;
                        if (listView != null) {
                            emptyView.setText(getString("NoResult", R.string.NoResult));
                            emptyView.showProgress();
                            listView.setAdapter(searchAdapter);
                            searchAdapter.notifyDataSetChanged();
                            listView.setFastScrollVisible(false);
                            listView.setVerticalScrollBarEnabled(true);
                        }
                    }
                    searchAdapter.searchDialogs(text);
                }
            });
            searchItem.setSearchFieldHint(getString("Search", R.string.Search));
        }

        searchAdapter = new SearchAdapter(context);

        fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));

        emptyView = new EmptyTextProgressView(context);
        emptyView.setTextSize(18);
        emptyView.setText(getString("NoExceptions", R.string.NoExceptions));
        emptyView.showTextView();
        frameLayout.addView(emptyView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));

<<<<<<< HEAD
        listView = new RecyclerListView(context);
=======
        listView = new RecyclerListView(context) {
            @Override
            protected void dispatchDraw(Canvas canvas) {
                if (currentType != -1) {
                    if (exceptionsStart >= 0) {
                        drawSectionBackground(canvas, exceptionsStart, exceptionsEnd, getThemedColor(Theme.key_windowBackgroundWhite));
                    }
                    if (currentType != TYPE_REACTIONS_MESSAGES && currentType != TYPE_REACTIONS_STORIES) {
                        drawSectionBackground(canvas, settingsStart, settingsEnd, getThemedColor(Theme.key_windowBackgroundWhite));
                    }
                }
                super.dispatchDraw(canvas);
            }
        };
//        listView.setTranslateSelector(true);
>>>>>>> d494ea8cb (update to 10.12.0 (4710))
        listView.setEmptyView(emptyView);
        listView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        listView.setVerticalScrollBarEnabled(false);
        frameLayout.addView(listView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        listView.setAdapter(adapter = new ListAdapter(context));
        listView.setOnItemClickListener((view, position, x, y) -> {
            boolean enabled = false;
            if (getParentActivity() == null) {
                return;
            }
<<<<<<< HEAD
            if (listView.getAdapter() == searchAdapter || position >= exceptionsStartRow && position < exceptionsEndRow) {
=======
            ItemInner item = null;
            if (listView.getAdapter() == adapter && position >= 0 && position < items.size()) {
                item = items.get(position);
            }
            if (item != null && item.viewType == VIEW_TYPE_EXPAND) {
                expanded = !expanded;
                updateRows(true);
                return;
            }
            if (currentType == TYPE_STORIES && item != null && item.exception != null) {
                NotificationsSettingsActivity.NotificationException exception = item.exception;
                ItemOptions.makeOptions(NotificationsCustomSettingsActivity.this, view)
                    .setGravity(Gravity.LEFT)
                    .addIf(exception.notify <= 0 || exception.auto, R.drawable.msg_mute, getString(R.string.NotificationsStoryMute), false, () -> {
                        updateMute(exception, view, position, false, true);
                    })
                    .addIf(exception.notify > 0 || exception.auto, R.drawable.msg_unmute, getString(R.string.NotificationsStoryUnmute), false, () -> {
                        updateMute(exception, view, position, false, false);
                    })
                    .addIf(!exception.auto, R.drawable.msg_delete, getString("DeleteException", R.string.DeleteException), true, () -> {
                        deleteException(exception, view, position);
                    })
                    .show();
                return;
            }
            if (currentType == TYPE_STORIES && listView.getAdapter() == searchAdapter) {
                NotificationsSettingsActivity.NotificationException exception;
                boolean newException;
                Object object = searchAdapter.getObject(position);
                if (object instanceof NotificationsSettingsActivity.NotificationException) {
                    exception = (NotificationsSettingsActivity.NotificationException) object;
                    newException = false;
                } else {
                    long did;
                    if (object instanceof TLRPC.User) {
                        TLRPC.User user = (TLRPC.User) object;
                        did = user.id;
                    } else {
                        TLRPC.Chat chat = (TLRPC.Chat) object;
                        did = -chat.id;
                    }
                    if (exceptionsDict.containsKey(did)) {
                        exception = exceptionsDict.get(did);
                        newException = false;
                    } else {
                        newException = true;
                        exception = new NotificationsSettingsActivity.NotificationException();
                        exception.story = true;
                        exception.did = did;
                        if (object instanceof TLRPC.User) {
                            TLRPC.User user = (TLRPC.User) object;
                            exception.did = user.id;
                        } else {
                            TLRPC.Chat chat = (TLRPC.Chat) object;
                            exception.did = -chat.id;
                        }
                    }
                }
                if (exception == null) {
                    return;
                }

                ItemOptions.makeOptions(NotificationsCustomSettingsActivity.this, view)
                    .setGravity(Gravity.LEFT)
                    .addIf(exception.notify <= 0 || exception.auto, R.drawable.msg_mute, getString(R.string.NotificationsStoryMute), false, () -> {
                        actionBar.closeSearchField();
                        updateMute(exception, view, -1, newException, true);
                    })
                    .addIf(exception.notify > 0 || exception.auto, R.drawable.msg_unmute, getString(R.string.NotificationsStoryUnmute), false, () -> {
                        actionBar.closeSearchField();
                        updateMute(exception, view, -1, newException, false);
                    })
                    .addIf(!newException && !exception.auto, R.drawable.msg_delete, getString("DeleteException", R.string.DeleteException), true, () -> {
                        deleteException(exception, view, position);
                    })
                    .show();
                return;
            }
            if (listView.getAdapter() == searchAdapter || item != null && item.exception != null) {
>>>>>>> d494ea8cb (update to 10.12.0 (4710))
                ArrayList<NotificationsSettingsActivity.NotificationException> arrayList;
                NotificationsSettingsActivity.NotificationException exception;
                boolean newException;
                if (listView.getAdapter() == searchAdapter) {
                    Object object = searchAdapter.getObject(position);
                    if (object instanceof NotificationsSettingsActivity.NotificationException) {
                        arrayList = searchAdapter.searchResult;
                        exception = (NotificationsSettingsActivity.NotificationException) object;
                        newException = false;
                    } else {
                        long did;
                        if (object instanceof TLRPC.User) {
                            TLRPC.User user = (TLRPC.User) object;
                            did = user.id;
                        } else {
                            TLRPC.Chat chat = (TLRPC.Chat) object;
                            did = -chat.id;
                        }
                        if (exceptionsDict.containsKey(did)) {
                            exception = exceptionsDict.get(did);
                            newException = false;
                        } else {
                            newException = true;
                            exception = new NotificationsSettingsActivity.NotificationException();
                            exception.did = did;
                            if (object instanceof TLRPC.User) {
                                TLRPC.User user = (TLRPC.User) object;
                                exception.did = user.id;
                            } else {
                                TLRPC.Chat chat = (TLRPC.Chat) object;
                                exception.did = -chat.id;
                            }
                        }
                        arrayList = exceptions;
                    }
                } else {
                    arrayList = exceptions;
                    int index = position - exceptionsStartRow;
                    if (index < 0 || index >= arrayList.size()) {
                        return;
                    }
                    exception = arrayList.get(index);
                    newException = false;
                }
                if (exception == null) {
                    return;
                }

                long did = exception.did;
                boolean defaultEnabled = NotificationsController.getInstance(currentAccount).isGlobalNotificationsEnabled(did, false, false);
                ChatNotificationsPopupWrapper chatNotificationsPopupWrapper = new ChatNotificationsPopupWrapper(context, currentAccount, null, true, true, new ChatNotificationsPopupWrapper.Callback() {
                    @Override
                    public void toggleSound() {
                        String key = NotificationsController.getSharedPrefKey(did, topicId);
                        SharedPreferences preferences = MessagesController.getNotificationsSettings(currentAccount);
                        boolean enabled = !preferences.getBoolean("sound_enabled_" + key, true);
                        preferences.edit().putBoolean("sound_enabled_" + key, enabled).apply();
                        if (BulletinFactory.canShowBulletin(NotificationsCustomSettingsActivity.this)) {
                            BulletinFactory.createSoundEnabledBulletin(NotificationsCustomSettingsActivity.this, enabled ? NotificationsController.SETTING_SOUND_ON : NotificationsController.SETTING_SOUND_OFF, getResourceProvider()).show();
                        }
                    }

                    @Override
                    public void muteFor(int timeInSeconds) {
                        if (timeInSeconds == 0) {
                            if (getMessagesController().isDialogMuted(did, topicId)) {
                                toggleMute();
                            }
                            if (BulletinFactory.canShowBulletin(NotificationsCustomSettingsActivity.this)) {
                                BulletinFactory.createMuteBulletin(NotificationsCustomSettingsActivity.this, NotificationsController.SETTING_MUTE_UNMUTE, timeInSeconds, getResourceProvider()).show();
                            }
                        } else {
                            getNotificationsController().muteUntil(did, topicId, timeInSeconds);
                            if (BulletinFactory.canShowBulletin(NotificationsCustomSettingsActivity.this)) {
                                BulletinFactory.createMuteBulletin(NotificationsCustomSettingsActivity.this, NotificationsController.SETTING_MUTE_CUSTOM, timeInSeconds, getResourceProvider()).show();
                            }
                        }
                        update();
                    }

                    @Override
                    public void showCustomize() {
                        if (did != 0) {
                            Bundle args = new Bundle();
                            args.putLong("dialog_id", did);
                            ProfileNotificationsActivity fragment = new ProfileNotificationsActivity(args);
                            fragment.setDelegate(new ProfileNotificationsActivity.ProfileNotificationsActivityDelegate() {
                                @Override
                                public void didCreateNewException(NotificationsSettingsActivity.NotificationException exception) {}

                                @Override
                                public void didRemoveException(long dialog_id) {
                                    setDefault();
                                }
                            });
                            presentFragment(fragment);
                        }
                    }

                    @Override
                    public void toggleMute() {
                        boolean muted = getMessagesController().isDialogMuted(did, topicId);
                        getNotificationsController().muteDialog(did, topicId, !muted);
                        BulletinFactory.createMuteBulletin(NotificationsCustomSettingsActivity.this, getMessagesController().isDialogMuted(did, topicId), null).show();
                        update();
                    }

                    private void update() {
                        if (getMessagesController().isDialogMuted(did, topicId) != defaultEnabled) {
                            setDefault();
                        } else {
                            setNotDefault();
                        }
                    }

                    private void setNotDefault() {
                        SharedPreferences preferences = getNotificationsSettings();
                        exception.hasCustom = preferences.getBoolean("custom_" + exception.did, false);
                        exception.notify = preferences.getInt("notify2_" + exception.did, 0);
                        if (exception.notify != 0) {
                            int time = preferences.getInt("notifyuntil_" + exception.did, -1);
                            if (time != -1) {
                                exception.muteUntil = time;
                            }
                        }
                        if (newException) {
                            exceptions.add(exception);
                            exceptionsDict.put(exception.did, exception);
                            updateRows(true);
                        } else {
                            listView.getAdapter().notifyItemChanged(position);
                        }
                        actionBar.closeSearchField();
                    }

                    private void setDefault() {
                        if (newException) {
                            return;
                        }
                        if (arrayList != exceptions) {
                            int idx = exceptions.indexOf(exception);
                            if (idx >= 0) {
                                exceptions.remove(idx);
                                exceptionsDict.remove(exception.did);
                            }
                        }
                        arrayList.remove(exception);
                        if (arrayList == exceptions) {
                            if (exceptionsAddRow != -1 && arrayList.isEmpty()) {
                                listView.getAdapter().notifyItemChanged(exceptionsAddRow);
                                listView.getAdapter().notifyItemRemoved(deleteAllRow);
                                listView.getAdapter().notifyItemRemoved(deleteAllSectionRow);
                            }
                            listView.getAdapter().notifyItemRemoved(position);
                            updateRows(false);
                            checkRowsEnabled();
                        } else {
                            updateRows(true);
                            searchAdapter.notifyItemChanged(position);
                        }
                        actionBar.closeSearchField();
                    }
                }, getResourceProvider());
                chatNotificationsPopupWrapper.update(did, topicId, null);
                chatNotificationsPopupWrapper.showAsOptions(NotificationsCustomSettingsActivity.this, view, x, y);
                return;
            }
            if (position == exceptionsAddRow) {
                Bundle args = new Bundle();
                args.putBoolean("onlySelect", true);
                args.putBoolean("checkCanWrite", false);
                if (currentType == NotificationsController.TYPE_GROUP) {
                    args.putInt("dialogsType", DialogsActivity.DIALOGS_TYPE_GROUPS_ONLY);
                } else if (currentType == NotificationsController.TYPE_CHANNEL) {
                    args.putInt("dialogsType", DialogsActivity.DIALOGS_TYPE_CHANNELS_ONLY);
                } else {
                    args.putInt("dialogsType", DialogsActivity.DIALOGS_TYPE_USERS_ONLY);
                }
                DialogsActivity activity = new DialogsActivity(args);
                activity.setDelegate((fragment, dids, message, param, topicsFragment) -> {
                    Bundle args2 = new Bundle();
                    args2.putLong("dialog_id", dids.get(0).dialogId);
                    args2.putBoolean("exception", true);
                    ProfileNotificationsActivity profileNotificationsActivity = new ProfileNotificationsActivity(args2, getResourceProvider());
                    profileNotificationsActivity.setDelegate(exception -> {
                        exceptions.add(0, exception);
                        updateRows(true);
                    });
                    presentFragment(profileNotificationsActivity, true);
                    return true;
                });
                presentFragment(activity);
            } else if (position == deleteAllRow) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
                builder.setTitle(getString("NotificationsDeleteAllExceptionTitle", R.string.NotificationsDeleteAllExceptionTitle));
                builder.setMessage(getString("NotificationsDeleteAllExceptionAlert", R.string.NotificationsDeleteAllExceptionAlert));
                builder.setPositiveButton(getString("Delete", R.string.Delete), (dialogInterface, i) -> {
                    SharedPreferences preferences = getNotificationsSettings();
                    SharedPreferences.Editor editor = preferences.edit();
                    for (int a = 0, N = exceptions.size(); a < N; a++) {
                        NotificationsSettingsActivity.NotificationException exception = exceptions.get(a);
                        editor.remove("notify2_" + exception.did).remove("custom_" + exception.did);
                        getMessagesStorage().setDialogFlags(exception.did, 0);
                        TLRPC.Dialog dialog = getMessagesController().dialogs_dict.get(exception.did);
                        if (dialog != null) {
                            dialog.notify_settings = new TLRPC.TL_peerNotifySettings();
                        }
                    }
                    editor.apply();
                    for (int a = 0, N = exceptions.size(); a < N; a++) {
                        NotificationsSettingsActivity.NotificationException exception = exceptions.get(a);
                        getNotificationsController().updateServerNotificationsSettings(exception.did, topicId, false);
                    }

                    exceptions.clear();
                    exceptionsDict.clear();
                    updateRows(true);
                    getNotificationCenter().postNotificationName(NotificationCenter.notificationsSettingsUpdated);
                });
                builder.setNegativeButton(getString("Cancel", R.string.Cancel), null);
                AlertDialog alertDialog = builder.create();
                showDialog(alertDialog);
                TextView button = (TextView) alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                if (button != null) {
                    button.setTextColor(Theme.getColor(Theme.key_text_RedBold));
                }
<<<<<<< HEAD
            } else if (position == alertRow) {
                enabled = getNotificationsController().isGlobalNotificationsEnabled(currentType);

                NotificationsCheckCell checkCell = (NotificationsCheckCell) view;
                RecyclerView.ViewHolder holder = listView.findViewHolderForAdapterPosition(position);
=======
            } else if (item.id == BUTTON_ENABLE || item.id == BUTTON_NEW_STORIES) {
                enabled = getNotificationsController().isGlobalNotificationsEnabled(currentType);
                TextCheckCell checkCell = (TextCheckCell) view;
                RecyclerView.ViewHolder holder = listView.findViewHolderForAdapterPosition(position);

                if (currentType == TYPE_STORIES) {
                    SharedPreferences preferences = getNotificationsSettings();
                    SharedPreferences.Editor editor = preferences.edit();
                    enabled = storiesEnabled != null && storiesEnabled;
                    if (storiesAuto && enabled) {
                        editor.remove("EnableAllStories");
                        storiesEnabled = null;
                    } else {
                        editor.putBoolean("EnableAllStories", !enabled);
                        storiesEnabled = !enabled;
                    }
                    editor.apply();
                    getNotificationsController().updateServerNotificationsSettings(currentType);
                    updateRows(true);
                    if (showAutoExceptions != (storiesEnabled == null)) {
                        toggleShowAutoExceptions();
                    }
                    checkRowsEnabled();
                    return;
                }

>>>>>>> d494ea8cb (update to 10.12.0 (4710))
                if (!enabled) {
                    getNotificationsController().setGlobalNotificationsEnabled(currentType, 0);
                    updateRows(true);
                } else {
<<<<<<< HEAD
                    AlertsCreator.showCustomNotificationsDialog(NotificationsCustomSettingsActivity.this, 0, 0, currentType, exceptions, currentAccount, param -> {
                        int offUntil;
                        SharedPreferences preferences = getNotificationsSettings();
                        if (currentType == NotificationsController.TYPE_PRIVATE) {
                            offUntil = preferences.getInt("EnableAll2", 0);
                        } else if (currentType == NotificationsController.TYPE_GROUP) {
                            offUntil = preferences.getInt("EnableGroup2", 0);
                        } else {
                            offUntil = preferences.getInt("EnableChannel2", 0);
                        }
                        int currentTime = getConnectionsManager().getCurrentTime();
                        int iconType;
                        if (offUntil < currentTime) {
                            iconType = 0;
                        } else if (offUntil - 60 * 60 * 24 * 365 >= currentTime) {
                            iconType = 0;
                        } else {
                            iconType = 2;
                        }
                        checkCell.setChecked(getNotificationsController().isGlobalNotificationsEnabled(currentType), iconType);
                        if (holder != null) {
                            adapter.onBindViewHolder(holder, position);
                        }
                        checkRowsEnabled();
=======
                    AlertsCreator.showCustomNotificationsDialog(NotificationsCustomSettingsActivity.this, 0, 0, currentType, exceptions, autoExceptions, currentAccount, param -> {
                        updateRows(true);
>>>>>>> d494ea8cb (update to 10.12.0 (4710))
                    });
                }
            } else if (position == previewRow) {
                if (!view.isEnabled()) {
                    return;
                }
                SharedPreferences preferences = getNotificationsSettings();
                SharedPreferences.Editor editor = preferences.edit();
                if (currentType == NotificationsController.TYPE_PRIVATE) {
                    enabled = preferences.getBoolean("EnablePreviewAll", true);
                    editor.putBoolean("EnablePreviewAll", !enabled);
                } else if (currentType == NotificationsController.TYPE_GROUP) {
                    enabled = preferences.getBoolean("EnablePreviewGroup", true);
                    editor.putBoolean("EnablePreviewGroup", !enabled);
                } else {
                    enabled = preferences.getBoolean("EnablePreviewChannel", true);
                    editor.putBoolean("EnablePreviewChannel", !enabled);
                }
                editor.apply();
                getNotificationsController().updateServerNotificationsSettings(currentType);
            } else if (position == messageSoundRow) {
                if (!view.isEnabled()) {
                    return;
                }
                try {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", currentType);
                    presentFragment(new NotificationsSoundActivity(bundle, getResourceProvider()));
//                    SharedPreferences preferences = getNotificationsSettings();
//                    Intent tmpIntent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
//                    tmpIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
//                    tmpIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
//                    tmpIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, true);
//                    tmpIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
//                    Uri currentSound = null;
//
//                    String defaultPath = null;
//                    Uri defaultUri = Settings.System.DEFAULT_NOTIFICATION_URI;
//                    if (defaultUri != null) {
//                        defaultPath = defaultUri.getPath();
//                    }
//
//                    String path;
//                    if (currentType == NotificationsController.TYPE_PRIVATE) {
//                        path = preferences.getString("GlobalSoundPath", defaultPath);
//                    } else if (currentType == NotificationsController.TYPE_GROUP) {
//                        path = preferences.getString("GroupSoundPath", defaultPath);
//                    } else {
//                        path = preferences.getString("ChannelSoundPath", defaultPath);
//                    }
//
//                    if (path != null && !path.equals("NoSound")) {
//                        if (path.equals(defaultPath)) {
//                            currentSound = defaultUri;
//                        } else {
//                            currentSound = Uri.parse(path);
//                        }
//                    }
//
//                    tmpIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currentSound);
//                    startActivityForResult(tmpIntent, position);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            } else if (position == messageLedRow) {
                if (!view.isEnabled()) {
                    return;
                }
                showDialog(AlertsCreator.createColorSelectDialog(getParentActivity(), 0, 0, currentType, () -> {
<<<<<<< HEAD
                    RecyclerView.ViewHolder holder = listView.findViewHolderForAdapterPosition(position);
                    if (holder != null) {
                        adapter.onBindViewHolder(holder, position);
=======
                    if (view instanceof TextColorCell) {
                        if (position >= 0 && position < items.size()) {
                            items.get(position).color = getLedColor();
                        }
                        ((TextColorCell) view).setTextAndColor(getString("LedColor", R.string.LedColor), getLedColor(), true);
                    } else {
                        updateRows(true);
>>>>>>> d494ea8cb (update to 10.12.0 (4710))
                    }
                }));
            } else if (position == messagePopupNotificationRow) {
                if (!view.isEnabled()) {
                    return;
                }
                showDialog(AlertsCreator.createPopupSelectDialog(getParentActivity(), currentType, () -> {
<<<<<<< HEAD
                    RecyclerView.ViewHolder holder = listView.findViewHolderForAdapterPosition(position);
                    if (holder != null) {
                        adapter.onBindViewHolder(holder, position);
=======
                    if (view instanceof TextSettingsCell) {
                        if (position >= 0 && position < items.size()) {
                            items.get(position).text2 = getPopupOption();
                        }
                        ((TextSettingsCell) view).setTextAndValue(getString("PopupNotification", R.string.PopupNotification), getPopupOption(), true, ((TextSettingsCell) view).needDivider);
                    } else {
                        updateRows(true);
>>>>>>> d494ea8cb (update to 10.12.0 (4710))
                    }
                }));
            } else if (position == messageVibrateRow) {
                if (!view.isEnabled()) {
                    return;
                }
                String key;
                if (currentType == NotificationsController.TYPE_PRIVATE) {
                    key = "vibrate_messages";
                } else if (currentType == NotificationsController.TYPE_GROUP) {
                    key = "vibrate_group";
<<<<<<< HEAD
=======
                } else if (currentType == TYPE_STORIES) {
                    key = "vibrate_stories";
                } else if (currentType == TYPE_REACTIONS_MESSAGES || currentType == TYPE_REACTIONS_STORIES) {
                    key = "vibrate_react";
>>>>>>> d494ea8cb (update to 10.12.0 (4710))
                } else {
                    key = "vibrate_channel";
                }
                showDialog(AlertsCreator.createVibrationSelectDialog(getParentActivity(), 0, 0, key, () -> {
<<<<<<< HEAD
                    RecyclerView.ViewHolder holder = listView.findViewHolderForAdapterPosition(position);
                    if (holder != null) {
                        adapter.onBindViewHolder(holder, position);
=======
                    if (view instanceof TextSettingsCell) {
                        String value = getString(vibrateLabels[Utilities.clamp(getNotificationsSettings().getInt(key, 0), vibrateLabels.length - 1, 0)]);
                        if (position >= 0 && position < items.size()) {
                            items.get(position).text2 = value;
                        }
                        ((TextSettingsCell) view).setTextAndValue(getString("Vibrate", R.string.Vibrate), value, true, true);
                    } else {
                        updateRows(true);
>>>>>>> d494ea8cb (update to 10.12.0 (4710))
                    }
                }));
            } else if (position == messagePriorityRow) {
                if (!view.isEnabled()) {
                    return;
                }
                showDialog(AlertsCreator.createPrioritySelectDialog(getParentActivity(), 0, 0, currentType, () -> {
<<<<<<< HEAD
                    RecyclerView.ViewHolder holder = listView.findViewHolderForAdapterPosition(position);
                    if (holder != null) {
                        adapter.onBindViewHolder(holder, position);
                    }
                }));
            }
            if (view instanceof TextCheckCell) {
                ((TextCheckCell) view).setChecked(!enabled);
=======
                    if (view instanceof TextSettingsCell) {
                        if (position >= 0 && position < items.size()) {
                            items.get(position).text2 = getPriorityOption();
                        }
                        ((TextSettingsCell) view).setTextAndValue(getString("NotificationsImportance", R.string.NotificationsImportance), getPriorityOption(), true, ((TextSettingsCell) view).needDivider);
                    } else {
                        updateRows(true);
                    }
                }));
            } else if (item.id == BUTTON_IMPORTANT_STORIES) {
                if (!view.isEnabled()) {
                    return;
                }
                SharedPreferences preferences = getNotificationsSettings();
                if (preferences.getBoolean("EnableAllStories", false)) {
                    return;
                }
                SharedPreferences.Editor editor = preferences.edit();
                if (storiesEnabled != null) {
                    editor.remove("EnableAllStories");
                    storiesEnabled = null;
                    item.checked = storiesAuto = true;
                } else {
                    editor.putBoolean("EnableAllStories", false);
                    storiesEnabled = false;
                    item.checked = storiesAuto = false;
                }
                if (view instanceof TextCheckCell) {
                    ((TextCheckCell) view).setChecked(storiesAuto);
                }
                editor.commit();
                if (storiesAuto != showAutoExceptions) {
                    toggleShowAutoExceptions();
                }
                getNotificationsController().updateServerNotificationsSettings(currentType);
                checkRowsEnabled();
            } else if (item.id == 0) {
                if (!view.isEnabled()) {
                    return;
                }
                SharedPreferences preferences = getNotificationsSettings();
                SharedPreferences.Editor editor = preferences.edit();
                if (currentType == TYPE_PRIVATE) {
                    enabled = preferences.getBoolean("EnablePreviewAll", true);
                    editor.putBoolean("EnablePreviewAll", !enabled);
                } else if (currentType == TYPE_GROUP) {
                    enabled = preferences.getBoolean("EnablePreviewGroup", true);
                    editor.putBoolean("EnablePreviewGroup", !enabled);
                } else if (currentType == TYPE_STORIES) {
                    enabled = !preferences.getBoolean("EnableHideStoriesSenders", false);
                    editor.putBoolean("EnableHideStoriesSenders", enabled);
                } else if (currentType == TYPE_REACTIONS_MESSAGES || currentType == TYPE_REACTIONS_STORIES) {
                    enabled = preferences.getBoolean("EnableReactionsPreview", true);
                    editor.putBoolean("EnableReactionsPreview", !enabled);
                } else {
                    enabled = preferences.getBoolean("EnablePreviewChannel", true);
                    editor.putBoolean("EnablePreviewChannel", !enabled);
                }
                editor.commit();
                getNotificationsController().updateServerNotificationsSettings(currentType);
                if (view instanceof TextCheckCell) {
                    ((TextCheckCell) view).setChecked(!enabled);
                }
            } else if (item != null && (item.id == BUTTON_MESSAGES_REACTIONS || item.id == BUTTON_STORIES_REACTIONS)) {
                final boolean check = LocaleController.isRTL ? x < dp(76) : x > view.getMeasuredWidth() - dp(76);

                SharedPreferences prefs = getNotificationsSettings();
                if (check) {
                    final String key = item.id == BUTTON_MESSAGES_REACTIONS ? "EnableReactionsMessages" : "EnableReactionsStories";
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean(key, !prefs.getBoolean(key, true));
                    editor.apply();
                    updateRows(true);
                    getNotificationsController().updateServerNotificationsSettings(currentType);
                } else {
                    final String key = item.id == BUTTON_MESSAGES_REACTIONS ? "EnableReactionsMessagesContacts" : "EnableReactionsStoriesContacts";
                    final LinearLayout linearLayout = new LinearLayout(context);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);

                    boolean[] contacts = new boolean[] { prefs.getBoolean(key, false) };

                    RadioColorCell[] cells = new RadioColorCell[2];
                    for (int a = 0; a < cells.length; ++a) {
                        cells[a] = new RadioColorCell(context, getResourceProvider());
                        cells[a].setPadding(dp(4), 0, dp(4), 0);
                        cells[a].setCheckColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
                        cells[a].setTextAndValue(getString(a == 0 ? R.string.NotifyAboutReactionsFromEveryone : R.string.NotifyAboutReactionsFromContacts), a == 0 ? !contacts[0] : contacts[0]);
                        cells[a].setBackground(Theme.createSelectorDrawable(Theme.getColor(Theme.key_listSelector), Theme.RIPPLE_MASK_ALL));
                        linearLayout.addView(cells[a]);
                        final int finalA = a;
                        cells[a].setOnClickListener(v -> {
                            contacts[0] = finalA == 1;
                            for (int i = 0; i < cells.length; ++i) {
                                cells[i].setChecked(contacts[0] == (i == 1), true);
                            }
                        });
                    }

                    showDialog(
                        new AlertDialog.Builder(getContext(), resourceProvider)
                            .setTitle(getString(R.string.NotifyAboutReactionsFrom))
                            .setView(linearLayout)
                            .setNegativeButton(getString(R.string.Cancel), null)
                            .setPositiveButton(getString(R.string.Save), (di, w) -> {
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putBoolean(key, contacts[0]);
                                editor.apply();
                                updateRows(true);
                                getNotificationsController().updateServerNotificationsSettings(currentType);
                            })
                            .create()
                    );
                }
>>>>>>> d494ea8cb (update to 10.12.0 (4710))
            }
        });

        listView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    AndroidUtilities.hideKeyboard(getParentActivity().getCurrentFocus());
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        return fragmentView;
    }

    private void checkRowsEnabled() {
        if (!exceptions.isEmpty()) {
            return;
        }
        int count = listView.getChildCount();
        ArrayList<Animator> animators = new ArrayList<>();
        boolean enabled = getNotificationsController().isGlobalNotificationsEnabled(currentType);
        for (int a = 0; a < count; a++) {
            View child = listView.getChildAt(a);
            RecyclerListView.Holder holder = (RecyclerListView.Holder) listView.getChildViewHolder(child);
<<<<<<< HEAD
=======
            int position = listView.getChildAdapterPosition(child);
            ItemInner item = null;
            if (position >= 0 && position < items.size()) {
                item = items.get(position);
            }
            final boolean enabled;
            if (item != null && (item.id == BUTTON_IMPORTANT_STORIES || item.id == BUTTON_NEW_STORIES || item.id == BUTTON_ENABLE)) {
                enabled = true;
            } else {
                enabled = globalEnabled;
            }
>>>>>>> d494ea8cb (update to 10.12.0 (4710))
            switch (holder.getItemViewType()) {
                case 0: {
                    HeaderCell headerCell = (HeaderCell) holder.itemView;
                    if (holder.getAdapterPosition() == messageSectionRow) {
                        headerCell.setEnabled(enabled, animators);
                    }
                    break;
                }
                case 1: {
                    TextCheckCell textCell = (TextCheckCell) holder.itemView;
                    textCell.setEnabled(enabled, animators);
                    break;
                }
                case 3: {
                    TextColorCell textCell = (TextColorCell) holder.itemView;
                    textCell.setEnabled(enabled, animators);
                    break;
                }
                case 5: {
                    TextSettingsCell textCell = (TextSettingsCell) holder.itemView;
                    textCell.setEnabled(enabled, animators);
                    break;
                }
            }
        }
        if (!animators.isEmpty()) {
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            animatorSet = new AnimatorSet();
            animatorSet.playTogether(animators);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animator) {
                    if (animator.equals(animatorSet)) {
                        animatorSet = null;
                    }
                }
            });
            animatorSet.setDuration(150);
            animatorSet.start();
        }
    }

    private void loadExceptions() {
        getMessagesStorage().getStorageQueue().postRunnable(() -> {
            ArrayList<NotificationsSettingsActivity.NotificationException> usersResult = new ArrayList<>();
            ArrayList<NotificationsSettingsActivity.NotificationException> chatsResult = new ArrayList<>();
            ArrayList<NotificationsSettingsActivity.NotificationException> channelsResult = new ArrayList<>();
            LongSparseArray<NotificationsSettingsActivity.NotificationException> waitingForLoadExceptions = new LongSparseArray<>();

            ArrayList<Long> usersToLoad = new ArrayList<>();
            ArrayList<Long> chatsToLoad = new ArrayList<>();
            ArrayList<Integer> encryptedChatsToLoad = new ArrayList<>();

            ArrayList<TLRPC.User> users = new ArrayList<>();
            ArrayList<TLRPC.Chat> chats = new ArrayList<>();
            ArrayList<TLRPC.EncryptedChat> encryptedChats = new ArrayList<>();
            long selfId = getUserConfig().clientUserId;

            SharedPreferences preferences = getNotificationsSettings();
            Map<String, ?> values = preferences.getAll();
            for (Map.Entry<String, ?> entry : values.entrySet()) {
                String key = entry.getKey();
                if (key.startsWith("notify2_")) {
                    key = key.replace("notify2_", "");

                    long did = Utilities.parseLong(key);
                    if (did != 0 && did != selfId) {
                        NotificationsSettingsActivity.NotificationException exception = new NotificationsSettingsActivity.NotificationException();
                        exception.did = did;
                        exception.hasCustom = preferences.getBoolean("custom_" + did, false);
                        exception.notify = (Integer) entry.getValue();
                        if (exception.notify != 0) {
                            Integer time = (Integer) values.get("notifyuntil_" + key);
                            if (time != null) {
                                exception.muteUntil = time;
                            }
                        }

                        if (DialogObject.isEncryptedDialog(did)) {
                            int encryptedChatId = DialogObject.getEncryptedChatId(did);
                            TLRPC.EncryptedChat encryptedChat = getMessagesController().getEncryptedChat(encryptedChatId);
                            if (encryptedChat == null) {
                                encryptedChatsToLoad.add(encryptedChatId);
                                waitingForLoadExceptions.put(did, exception);
                            } else {
                                TLRPC.User user = getMessagesController().getUser(encryptedChat.user_id);
                                if (user == null) {
                                    usersToLoad.add(encryptedChat.user_id);
                                    waitingForLoadExceptions.put(encryptedChat.user_id, exception);
                                } else if (user.deleted) {
                                    continue;
                                }
                            }
                            usersResult.add(exception);
                        } else if (DialogObject.isUserDialog(did)) {
                            TLRPC.User user = getMessagesController().getUser(did);
                            if (user == null) {
                                usersToLoad.add(did);
                                waitingForLoadExceptions.put(did, exception);
                            } else if (user.deleted) {
                                continue;
                            }
                            usersResult.add(exception);
                        } else {
                            TLRPC.Chat chat = getMessagesController().getChat(-did);
                            if (chat == null) {
                                chatsToLoad.add(-did);
                                waitingForLoadExceptions.put(did, exception);
                                continue;
                            } else if (chat.left || chat.kicked || chat.migrated_to != null) {
                                continue;
                            }
                            if (ChatObject.isChannel(chat) && !chat.megagroup) {
                                channelsResult.add(exception);
                            } else {
                                chatsResult.add(exception);
                            }
                        }
                    }
                }
            }
            if (waitingForLoadExceptions.size() != 0) {
                try {
                    if (!encryptedChatsToLoad.isEmpty()) {
                        getMessagesStorage().getEncryptedChatsInternal(TextUtils.join(",", encryptedChatsToLoad), encryptedChats, usersToLoad);
                    }
                    if (!usersToLoad.isEmpty()) {
                        getMessagesStorage().getUsersInternal(TextUtils.join(",", usersToLoad), users);
                    }
                    if (!chatsToLoad.isEmpty()) {
                        getMessagesStorage().getChatsInternal(TextUtils.join(",", chatsToLoad), chats);
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
                for (int a = 0, size = chats.size(); a < size; a++) {
                    TLRPC.Chat chat = chats.get(a);
                    if (chat.left || chat.kicked || chat.migrated_to != null) {
                        continue;
                    }
                    NotificationsSettingsActivity.NotificationException exception = waitingForLoadExceptions.get(-chat.id);
                    waitingForLoadExceptions.remove(-chat.id);

                    if (exception != null) {
                        if (ChatObject.isChannel(chat) && !chat.megagroup) {
                            channelsResult.add(exception);
                        } else {
                            chatsResult.add(exception);
                        }
                    }
                }
                for (int a = 0, size = users.size(); a < size; a++) {
                    TLRPC.User user = users.get(a);
                    if (user.deleted) {
                        continue;
                    }
                    waitingForLoadExceptions.remove(user.id);
                }
                for (int a = 0, size = encryptedChats.size(); a < size; a++) {
                    TLRPC.EncryptedChat encryptedChat = encryptedChats.get(a);
                    waitingForLoadExceptions.remove(DialogObject.makeEncryptedDialogId(encryptedChat.id));
                }
                for (int a = 0, size = waitingForLoadExceptions.size(); a < size; a++) {
                    long did = waitingForLoadExceptions.keyAt(a);
                    if (DialogObject.isChatDialog(did)) {
                        chatsResult.remove(waitingForLoadExceptions.valueAt(a));
                        channelsResult.remove(waitingForLoadExceptions.valueAt(a));
                    } else {
                        usersResult.remove(waitingForLoadExceptions.valueAt(a));
                    }
                }
            }
            AndroidUtilities.runOnUIThread(() -> {
                getMessagesController().putUsers(users, true);
                getMessagesController().putChats(chats, true);
                getMessagesController().putEncryptedChats(encryptedChats, true);
                if (currentType == NotificationsController.TYPE_PRIVATE) {
                    exceptions = usersResult;
                } else if (currentType == NotificationsController.TYPE_GROUP) {
                    exceptions = chatsResult;
                } else {
                    exceptions = channelsResult;
                }
                updateRows(true);
            });
        });
    }

<<<<<<< HEAD
    private void updateRows(boolean notify) {
        rowCount = 0;
        if (currentType != -1) {
            alertRow = rowCount++;
            alertSection2Row = rowCount++;
            messageSectionRow = rowCount++;
            previewRow = rowCount++;
            messageLedRow = rowCount++;
            messageVibrateRow = rowCount++;
            if (currentType == NotificationsController.TYPE_CHANNEL) {
                messagePopupNotificationRow = -1;
            } else {
                messagePopupNotificationRow = rowCount++;
            }
            messageSoundRow = rowCount++;
            messagePriorityRow = rowCount++;
            groupSection2Row = rowCount++;
            exceptionsAddRow = rowCount++;
        } else {
            alertRow = -1;
            alertSection2Row = -1;
            messageSectionRow = -1;
            previewRow = -1;
            messageLedRow = -1;
            messageVibrateRow = -1;
            messagePopupNotificationRow = -1;
            messageSoundRow = -1;
            messagePriorityRow = -1;
            groupSection2Row = -1;
            exceptionsAddRow = -1;
        }
        if (exceptions != null && !exceptions.isEmpty()) {
            exceptionsStartRow = rowCount;
            rowCount += exceptions.size();
            exceptionsEndRow = rowCount;
        } else {
            exceptionsStartRow = -1;
            exceptionsEndRow = -1;
=======
    private final int[] vibrateLabels = new int[] {
        R.string.VibrationDefault,
        R.string.Short,
        R.string.VibrationDisabled,
        R.string.Long,
        R.string.OnlyIfSilent
    };

    private final int[] popupOptions = new int[] {
        R.string.NoPopup,
        R.string.OnlyWhenScreenOn,
        R.string.OnlyWhenScreenOff,
        R.string.AlwaysShowPopup
    };

    private final int[] priorityOptions = new int[] {
        R.string.NotificationsPriorityHigh,
        R.string.NotificationsPriorityUrgent,
        R.string.NotificationsPriorityUrgent,
        R.string.NotificationsPriorityMedium,
        R.string.NotificationsPriorityLow,
        R.string.NotificationsPriorityMedium
    };

    private static final int BUTTON_ENABLE = 100;
    private static final int BUTTON_NEW_STORIES = 101;
    private static final int BUTTON_IMPORTANT_STORIES = 102;
    private static final int BUTTON_MESSAGES_REACTIONS = 103;
    private static final int BUTTON_STORIES_REACTIONS = 104;

    boolean expanded;

    private void updateRows(boolean animated) {
        oldItems.clear();
        oldItems.addAll(items);
        items.clear();
        SharedPreferences prefs = getNotificationsSettings();
        boolean enabled = false;
        if (currentType != -1) {
            items.add(ItemInner.asHeader(getString(R.string.NotifyMeAbout)));
            if (currentType == TYPE_STORIES) {
                items.add(ItemInner.asCheck(BUTTON_NEW_STORIES, getString(R.string.NotifyMeAboutNewStories), prefs.getBoolean("EnableAllStories", false)));
                if (!prefs.getBoolean("EnableAllStories", false)) {
                    items.add(ItemInner.asCheck(BUTTON_IMPORTANT_STORIES, getString(R.string.NotifyMeAboutImportantStories), storiesAuto && (storiesEnabled == null || !storiesEnabled)));
                }
                items.add(ItemInner.asShadow(-1, getString(R.string.StoryAutoExceptionsInfo)));
            } else if (currentType == TYPE_REACTIONS_MESSAGES || currentType == TYPE_REACTIONS_STORIES) {
                items.add(ItemInner.asCheck2(
                    BUTTON_MESSAGES_REACTIONS,
                    R.drawable.msg_markunread,
                    getString(R.string.NotifyMeAboutMessagesReactions),
                    getString(
                        !prefs.getBoolean("EnableReactionsMessages", true) ?
                            R.string.NotifyFromNobody :
                        prefs.getBoolean("EnableReactionsMessagesContacts", false) ?
                            R.string.NotifyFromContacts :
                            R.string.NotifyFromEveryone
                    ),
                    prefs.getBoolean("EnableReactionsMessages", true)
                ));
                items.add(ItemInner.asCheck2(
                    BUTTON_STORIES_REACTIONS,
                    R.drawable.msg_stories_saved,
                    getString(R.string.NotifyMeAboutStoriesReactions),
                    getString(
                        !prefs.getBoolean("EnableReactionsStories", true) ?
                            R.string.NotifyFromNobody :
                        prefs.getBoolean("EnableReactionsStoriesContacts", false) ?
                            R.string.NotifyFromContacts :
                            R.string.NotifyFromEveryone
                    ),
                    prefs.getBoolean("EnableReactionsStories", true)
                ));
                items.add(ItemInner.asShadow(-1, null));
            } else {
                int text;
                if (currentType == TYPE_PRIVATE) {
                    text = R.string.NotifyMeAboutPrivate;
                } else if (currentType == TYPE_GROUP) {
                    text = R.string.NotifyMeAboutGroups;
                } else {
                    text = R.string.NotifyMeAboutChannels;
                }
                items.add(ItemInner.asCheck(BUTTON_ENABLE, getString(text), getNotificationsController().isGlobalNotificationsEnabled(currentType)));
                items.add(ItemInner.asShadow(-1, null));
            }
            items.add(ItemInner.asHeader(getString(R.string.SETTINGS)));
            settingsStart = items.size() - 1;

            if (currentType == TYPE_STORIES) {
                items.add(ItemInner.asCheck(0, getString(R.string.NotificationShowSenderNames), !prefs.getBoolean("EnableHideStoriesSenders", false)));
            } else if (currentType == TYPE_REACTIONS_MESSAGES || currentType == TYPE_REACTIONS_STORIES) {
                items.add(ItemInner.asCheck(0, getString(R.string.NotificationShowSenderNames), prefs.getBoolean("EnableReactionsPreview", true)));
            } else {
                switch (currentType) {
                    case TYPE_PRIVATE: enabled = prefs.getBoolean("EnablePreviewAll", true); break;
                    case TYPE_GROUP:   enabled = prefs.getBoolean("EnablePreviewGroup", true); break;
                    case TYPE_CHANNEL: enabled = prefs.getBoolean("EnablePreviewChannel", true); break;
                }
                items.add(ItemInner.asCheck(0, getString(R.string.MessagePreview), enabled));
            }

            items.add(ItemInner.asSetting(3, getString("Sound", R.string.Sound), getSound()));

            if (expanded) {

                items.add(ItemInner.asColor(getString("LedColor", R.string.LedColor), getLedColor()));

                int vibrate = 0;
                switch (currentType) {
                    case TYPE_PRIVATE:
                        vibrate = prefs.getInt("vibrate_messages", 0);
                        break;
                    case TYPE_GROUP:
                        vibrate = prefs.getInt("vibrate_group", 0);
                        break;
                    case TYPE_STORIES:
                        vibrate = prefs.getInt("vibrate_stories", 0);
                        break;
                    case TYPE_CHANNEL:
                        vibrate = prefs.getInt("vibrate_channel", 0);
                        break;
                    case TYPE_REACTIONS_MESSAGES:
                    case TYPE_REACTIONS_STORIES:
                        vibrate = prefs.getInt("vibrate_react", 0);
                        break;
                }
                items.add(ItemInner.asSetting(1, getString("Vibrate", R.string.Vibrate), getString(vibrateLabels[Utilities.clamp(vibrate, vibrateLabels.length - 1, 0)])));

                if (currentType == TYPE_PRIVATE || currentType == TYPE_GROUP) {
                    items.add(ItemInner.asSetting(2, getString("PopupNotification", R.string.PopupNotification), getPopupOption()));
                }

                if (Build.VERSION.SDK_INT >= 21) {
                    items.add(ItemInner.asSetting(4, getString("NotificationsImportance", R.string.NotificationsImportance), getPriorityOption()));
                }

                items.add(ItemInner.asExpand(getString(R.string.NotifyLessOptions), false));
            } else {
                items.add(ItemInner.asExpand(getString(R.string.NotifyMoreOptions), true));
            }
            settingsEnd = items.size() - 1;

            items.add(ItemInner.asShadow(-2, null));
        }
        if (currentType != TYPE_REACTIONS_MESSAGES && currentType != TYPE_REACTIONS_STORIES) {
            if (currentType != -1) {
                items.add(ItemInner.asButton(6, R.drawable.msg_contact_add, getString("NotificationsAddAnException", R.string.NotificationsAddAnException)));
            }
            exceptionsStart = items.size() - 1;
            if (autoExceptions != null && showAutoExceptions) {
                for (int i = 0; i < autoExceptions.size(); ++i) {
                    items.add(ItemInner.asException(autoExceptions.get(i)));
                }
            }
            if (exceptions != null) {
                for (int i = 0; i < exceptions.size(); ++i) {
                    items.add(ItemInner.asException(exceptions.get(i)));
                }
            }
            exceptionsEnd = items.size() - 1;
            if (currentType != -1 || exceptions != null && !exceptions.isEmpty()) {
                items.add(ItemInner.asShadow(-3, null));
            }
            if (exceptions != null && !exceptions.isEmpty()) {
                items.add(ItemInner.asButton(7, 0, getString("NotificationsDeleteAllException", R.string.NotificationsDeleteAllException)));
            }
        } else {
            exceptionsStart = -1;
            exceptionsEnd = -1;
>>>>>>> d494ea8cb (update to 10.12.0 (4710))
        }
        if (currentType != -1 || exceptions != null && !exceptions.isEmpty()) {
            exceptionsSection2Row = rowCount++;
        } else {
            exceptionsSection2Row = -1;
        }
        if (exceptions != null && !exceptions.isEmpty()) {
            deleteAllRow = rowCount++;
            deleteAllSectionRow = rowCount++;
        } else {
            deleteAllRow = -1;
            deleteAllSectionRow = -1;
        }
        if (notify && adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResultFragment(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri ringtone = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            String name = null;
            if (ringtone != null) {
                Ringtone rng = RingtoneManager.getRingtone(getParentActivity(), ringtone);
                if (rng != null) {
                    if (ringtone.equals(Settings.System.DEFAULT_NOTIFICATION_URI)) {
                        name = getString("SoundDefault", R.string.SoundDefault);
                    } else {
                        name = rng.getTitle(getParentActivity());
                    }
                    rng.stop();
                }
            }

            SharedPreferences preferences = getNotificationsSettings();
            SharedPreferences.Editor editor = preferences.edit();

            if (currentType == NotificationsController.TYPE_PRIVATE) {
                if (name != null && ringtone != null) {
                    editor.putString("GlobalSound", name);
                    editor.putString("GlobalSoundPath", ringtone.toString());
                } else {
                    editor.putString("GlobalSound", "NoSound");
                    editor.putString("GlobalSoundPath", "NoSound");
                }
            } else if (currentType == NotificationsController.TYPE_GROUP) {
                if (name != null && ringtone != null) {
                    editor.putString("GroupSound", name);
                    editor.putString("GroupSoundPath", ringtone.toString());
                } else {
                    editor.putString("GroupSound", "NoSound");
                    editor.putString("GroupSoundPath", "NoSound");
                }
            } else if (currentType == NotificationsController.TYPE_CHANNEL) {
                if (name != null && ringtone != null) {
                    editor.putString("ChannelSound", name);
                    editor.putString("ChannelSoundPath", ringtone.toString());
                } else {
                    editor.putString("ChannelSound", "NoSound");
                    editor.putString("ChannelSoundPath", "NoSound");
                }
            }
            getNotificationsController().deleteNotificationChannelGlobal(currentType);
            editor.apply();
            getNotificationsController().updateServerNotificationsSettings(currentType);
            RecyclerView.ViewHolder holder = listView.findViewHolderForAdapterPosition(requestCode);
            if (holder != null) {
                adapter.onBindViewHolder(holder, requestCode);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        getNotificationCenter().addObserver(this, NotificationCenter.notificationsSettingsUpdated);
    }

    @Override
    public void onPause() {
        super.onPause();
        getNotificationCenter().removeObserver(this, NotificationCenter.notificationsSettingsUpdated);
    }

    @Override
    public void didReceivedNotification(int id, int account, Object... args) {
        if (id == NotificationCenter.notificationsSettingsUpdated) {
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    private class SearchAdapter extends RecyclerListView.SelectionAdapter {

        private Context mContext;
        private ArrayList<NotificationsSettingsActivity.NotificationException> searchResult = new ArrayList<>();
        private ArrayList<CharSequence> searchResultNames = new ArrayList<>();
        private Runnable searchRunnable;
        private SearchAdapterHelper searchAdapterHelper;

        public SearchAdapter(Context context) {
            mContext = context;
            searchAdapterHelper = new SearchAdapterHelper(true);
            searchAdapterHelper.setDelegate((searchId) -> {
                if (searchRunnable == null && !searchAdapterHelper.isSearchInProgress()) {
                    emptyView.showTextView();
                }
                notifyDataSetChanged();
            });
        }

        public void searchDialogs(final String query) {
            if (searchRunnable != null) {
                Utilities.searchQueue.cancelRunnable(searchRunnable);
                searchRunnable = null;
            }
            if (query == null) {
                searchResult.clear();
                searchResultNames.clear();
                searchAdapterHelper.mergeResults(null);
                searchAdapterHelper.queryServerSearch(null, true, currentType != NotificationsController.TYPE_PRIVATE, true, false, false, 0, false, 0, 0);
                notifyDataSetChanged();
            } else {
                Utilities.searchQueue.postRunnable(searchRunnable = () -> processSearch(query), 300);
            }
        }

        private void processSearch(final String query) {
            AndroidUtilities.runOnUIThread(() -> {
                searchAdapterHelper.queryServerSearch(query, true, currentType != NotificationsController.TYPE_PRIVATE, true, false, false, 0, false, 0, 0);
                final ArrayList<NotificationsSettingsActivity.NotificationException> contactsCopy = new ArrayList<>(exceptions);
                Utilities.searchQueue.postRunnable(() -> {
                    String search1 = query.trim().toLowerCase();
                    if (search1.length() == 0) {
                        updateSearchResults(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                        return;
                    }
                    String search2 = LocaleController.getInstance().getTranslitString(search1);
                    if (search1.equals(search2) || search2.length() == 0) {
                        search2 = null;
                    }
                    String[] search = new String[1 + (search2 != null ? 1 : 0)];
                    search[0] = search1;
                    if (search2 != null) {
                        search[1] = search2;
                    }

                    ArrayList<Object> resultArray = new ArrayList<>();
                    ArrayList<NotificationsSettingsActivity.NotificationException> exceptionsArray = new ArrayList<>();
                    ArrayList<CharSequence> resultArrayNames = new ArrayList<>();

                    String[] names = new String[2];
                    for (int a = 0; a < contactsCopy.size(); a++) {
                        NotificationsSettingsActivity.NotificationException exception = contactsCopy.get(a);

                        TLObject object = null;

                        if (DialogObject.isEncryptedDialog(exception.did)) {
                            TLRPC.EncryptedChat encryptedChat = getMessagesController().getEncryptedChat(DialogObject.getEncryptedChatId(exception.did));
                            if (encryptedChat != null) {
                                TLRPC.User user = getMessagesController().getUser(encryptedChat.user_id);
                                if (user != null) {
                                    names[0] = ContactsController.formatName(user.first_name, user.last_name);
                                    names[1] = UserObject.getPublicUsername(user);
                                }
                            }
                        } else if (DialogObject.isUserDialog(exception.did)) {
                            TLRPC.User user = getMessagesController().getUser(exception.did);
                            if (user == null || user.deleted) {
                                continue;
                            }
                            names[0] = ContactsController.formatName(user.first_name, user.last_name);
                            names[1] = UserObject.getPublicUsername(user);
                            object = user;
                        } else {
                            TLRPC.Chat chat = getMessagesController().getChat(-exception.did);
                            if (chat != null) {
                                if (chat.left || chat.kicked || chat.migrated_to != null) {
                                    continue;
                                }
                                names[0] = chat.title;
                                names[1] = ChatObject.getPublicUsername(chat);
                                object = chat;
                            }
                        }

                        String originalName = names[0];
                        names[0] = names[0].toLowerCase();
                        String tName = LocaleController.getInstance().getTranslitString(names[0]);
                        if (names[0] != null && names[0].equals(tName)) {
                            tName = null;
                        }

                        int found = 0;
                        for (int b = 0; b < search.length; b++) {
                            String q = search[b];
                            if (names[0] != null && (names[0].startsWith(q) || names[0].contains(" " + q)) || tName != null && (tName.startsWith(q) || tName.contains(" " + q))) {
                                found = 1;
                            } else if (names[1] != null && names[1].startsWith(q)) {
                                found = 2;
                            }

                            if (found != 0) {
                                if (found == 1) {
                                    resultArrayNames.add(AndroidUtilities.generateSearchName(originalName, null, q));
                                } else {
                                    resultArrayNames.add(AndroidUtilities.generateSearchName("@" + names[1], null, "@" + q));
                                }
                                exceptionsArray.add(exception);
                                if (object != null) {
                                    resultArray.add(object);
                                }
                                break;
                            }
                        }
                    }
                    updateSearchResults(resultArray, exceptionsArray, resultArrayNames);
                });
            });
        }

        private void updateSearchResults(final ArrayList<Object> result, final ArrayList<NotificationsSettingsActivity.NotificationException> exceptions, final ArrayList<CharSequence> names) {
            AndroidUtilities.runOnUIThread(() -> {
                if (!searching) {
                    return;
                }
                searchRunnable = null;
                searchResult = exceptions;
                searchResultNames = names;
                searchAdapterHelper.mergeResults(result);
                if (searching && !searchAdapterHelper.isSearchInProgress()) {
                    emptyView.showTextView();
                }
                notifyDataSetChanged();
            });
        }

        public Object getObject(int position) {
            if (position >= 0 && position < searchResult.size()) {
                return searchResult.get(position);
            } else {
                position -= searchResult.size() + 1;
                ArrayList<TLObject> globalSearch = searchAdapterHelper.getGlobalSearch();
                if (position >= 0 && position < globalSearch.size()) {
                    return searchAdapterHelper.getGlobalSearch().get(position);
                }
            }
            return null;
        }

        @Override
        public boolean isEnabled(RecyclerView.ViewHolder holder) {
            return true;
        }

        @Override
        public int getItemCount() {
            int count = searchResult.size();
            ArrayList<TLObject> globalSearch = searchAdapterHelper.getGlobalSearch();
            if (!globalSearch.isEmpty()) {
                count += 1 + globalSearch.size();
            }
            return count;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case 0: {
                    view = new UserCell(mContext, 4, 0, false, true);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                }
                case 1:
                default: {
                    view = new GraySectionCell(mContext);
                    break;
                }
            }

            return new RecyclerListView.Holder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case 0: {
                    UserCell cell = (UserCell) holder.itemView;
                    if (position < searchResult.size()) {
                        cell.setException(searchResult.get(position), searchResultNames.get(position), position != searchResult.size() - 1);
                        cell.setAddButtonVisible(false);
                    } else {
                        position -= searchResult.size() + 1;
                        ArrayList<TLObject> globalSearch = searchAdapterHelper.getGlobalSearch();
                        TLObject object = globalSearch.get(position);
                        cell.setData(object, null, getString("NotificationsOn", R.string.NotificationsOn), 0, position != globalSearch.size() - 1);
                        cell.setAddButtonVisible(true);
                    }
                    break;
                }
                case 1: {
                    GraySectionCell cell = (GraySectionCell) holder.itemView;
                    cell.setText(getString("AddToExceptions", R.string.AddToExceptions));
                    break;
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == searchResult.size()) {
                return 1;
            }
            return 0;
        }
    }

<<<<<<< HEAD
    private class ListAdapter extends RecyclerListView.SelectionAdapter {
=======
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_CHECK = 1;
    private static final int VIEW_TYPE_USER = 2;
    private static final int VIEW_TYPE_COLOR = 3;
    private static final int VIEW_TYPE_SHADOW = 4;
    private static final int VIEW_TYPE_SETTING = 5;
    private static final int VIEW_TYPE_CHECK2 = 6;
    private static final int VIEW_TYPE_BUTTON = 7;
    private static final int VIEW_TYPE_EXPAND = 8;

    private static class ItemInner extends AdapterWithDiffUtils.Item {

        public int id;
        public int resId;
        public CharSequence text, text2;
        public NotificationsSettingsActivity.NotificationException exception;
        public int color;
        public boolean checked;

        private ItemInner(int viewType) {
            super(viewType, true);
        }

        public static ItemInner asHeader(CharSequence text) {
            ItemInner item = new ItemInner(VIEW_TYPE_HEADER);
            item.text = text;
            return item;
        }
        public static ItemInner asCheck(int id, CharSequence text, boolean checked) {
            ItemInner item = new ItemInner(VIEW_TYPE_CHECK);
            item.id = id;
            item.text = text;
            item.checked = checked;
            return item;
        }
        public static ItemInner asCheck2(int id, int icon, CharSequence text, CharSequence subtext, boolean checked) {
            ItemInner item = new ItemInner(VIEW_TYPE_CHECK2);
            item.id = id;
            item.resId = icon;
            item.text = text;
            item.text2 = subtext;
            item.checked = checked;
            return item;
        }
        public static ItemInner asException(NotificationsSettingsActivity.NotificationException exception) {
            ItemInner item = new ItemInner(VIEW_TYPE_USER);
            item.exception = exception;
            return item;
        }
        public static ItemInner asColor(CharSequence text, int color) {
            ItemInner item = new ItemInner(VIEW_TYPE_COLOR);
            item.text = text;
            item.color = color;
            return item;
        }
        public static ItemInner asShadow(int id, CharSequence text) {
            ItemInner item = new ItemInner(VIEW_TYPE_SHADOW);
            item.id = id;
            item.text = text;
            return item;
        }
        public static ItemInner asSetting(int id, CharSequence text, CharSequence value) {
            ItemInner item = new ItemInner(VIEW_TYPE_SETTING);
            item.id = id;
            item.text = text;
            item.text2 = value;
            return item;
        }
        public static ItemInner asButton(int id, int resId, CharSequence text) {
            ItemInner item = new ItemInner(VIEW_TYPE_BUTTON);
            item.id = id;
            item.resId = resId;
            item.text = text;
            return item;
        }
        public static ItemInner asExpand(CharSequence text, boolean expand) {
            ItemInner item = new ItemInner(VIEW_TYPE_EXPAND);
            item.text = text;
            item.resId = expand ? 1 : 0;
            return item;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ItemInner itemInner = (ItemInner) o;
            return id == itemInner.id && color == itemInner.color && (viewType == VIEW_TYPE_EXPAND || resId == itemInner.resId && Objects.equals(text, itemInner.text) && (viewType == VIEW_TYPE_CHECK2 || Objects.equals(text2, itemInner.text2))) && exception == itemInner.exception;
        }

        @Override
        protected boolean contentsEquals(AdapterWithDiffUtils.Item o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ItemInner itemInner = (ItemInner) o;
            return id == itemInner.id && resId == itemInner.resId && color == itemInner.color && checked == itemInner.checked && Objects.equals(text, itemInner.text) && Objects.equals(text2, itemInner.text2) && exception == itemInner.exception;
        }
    }

    private final ArrayList<ItemInner> oldItems = new ArrayList<>();
    private final ArrayList<ItemInner> items = new ArrayList<>();

    private class ListAdapter extends AdapterWithDiffUtils {
>>>>>>> d494ea8cb (update to 10.12.0 (4710))

        private Context mContext;

        public ListAdapter(Context context) {
            mContext = context;
        }

        @Override
        public boolean isEnabled(RecyclerView.ViewHolder holder) {
            int type = holder.getItemViewType();
            return type != 0 && type != 4;
        }

        @Override
        public int getItemCount() {
            return rowCount;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case 0:
                    view = new HeaderCell(mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 1:
                    view = new TextCheckCell(mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 2:
                    view = new UserCell(mContext, 6, 0, false);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 3:
                    view = new TextColorCell(mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 4:
                    view = new ShadowSectionCell(mContext);
                    break;
                case 5:
                    view = new TextSettingsCell(mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
<<<<<<< HEAD
                case 6:
                    view = new NotificationsCheckCell(mContext);
=======
                case VIEW_TYPE_CHECK2:
                    view = new NotificationsCheckCell(mContext, 21, 64, true, resourceProvider);
>>>>>>> d494ea8cb (update to 10.12.0 (4710))
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 7:
                default:
                    view = new TextCell(mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case VIEW_TYPE_EXPAND:
                    view = new ExpandView(mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
            }
            return new RecyclerListView.Holder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case 0: {
                    HeaderCell headerCell = (HeaderCell) holder.itemView;
                    if (position == messageSectionRow) {
                        headerCell.setText(LocaleController.getString("SETTINGS", R.string.SETTINGS));
                    }
                    break;
                }
                case 1: {
                    TextCheckCell checkCell = (TextCheckCell) holder.itemView;
                    SharedPreferences preferences = getNotificationsSettings();
                    if (position == previewRow) {
                        boolean enabled;
                        if (currentType == NotificationsController.TYPE_PRIVATE) {
                            enabled = preferences.getBoolean("EnablePreviewAll", true);
                        } else if (currentType == NotificationsController.TYPE_GROUP) {
                            enabled = preferences.getBoolean("EnablePreviewGroup", true);
                        } else {
                            enabled = preferences.getBoolean("EnablePreviewChannel", true);
                        }
                        checkCell.setTextAndCheck(LocaleController.getString("MessagePreview", R.string.MessagePreview), enabled, true);
                    }
                    break;
                }
                case 2: {
                    UserCell cell = (UserCell) holder.itemView;
                    NotificationsSettingsActivity.NotificationException exception = exceptions.get(position - exceptionsStartRow);
                    cell.setException(exception, null, position != exceptionsEndRow - 1);
                    break;
                }
                case 3: {
                    TextColorCell textColorCell = (TextColorCell) holder.itemView;
                    SharedPreferences preferences = getNotificationsSettings();
                    int color;
                    if (currentType == NotificationsController.TYPE_PRIVATE) {
                        color = preferences.getInt("MessagesLed", 0xff0000ff);
                    } else if (currentType == NotificationsController.TYPE_GROUP) {
                        color = preferences.getInt("GroupLed", 0xff0000ff);
                    } else {
                        color = preferences.getInt("ChannelLed", 0xff0000ff);
                    }
                    for (int a = 0; a < 9; a++) {
                        if (TextColorCell.colorsToSave[a] == color) {
                            color = TextColorCell.colors[a];
                            break;
                        }
                    }
                    textColorCell.setTextAndColor(LocaleController.getString("LedColor", R.string.LedColor), color, true);
                    break;
                }
                case 4: {
                    if (position == deleteAllSectionRow || position == groupSection2Row && exceptionsSection2Row == -1 || position == exceptionsSection2Row && deleteAllRow == -1) {
                        holder.itemView.setBackgroundDrawable(Theme.getThemedDrawableByKey(mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    } else {
                        holder.itemView.setBackgroundDrawable(Theme.getThemedDrawableByKey(mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                    }
                    break;
                }
                case 5: {
                    TextSettingsCell textCell = (TextSettingsCell) holder.itemView;
                    SharedPreferences preferences = getNotificationsSettings();
                    if (position == messageSoundRow) {
                        String value;
                        long documentId;
                        if (currentType == NotificationsController.TYPE_PRIVATE) {
                            value = preferences.getString("GlobalSound", LocaleController.getString("SoundDefault", R.string.SoundDefault));
                            documentId = preferences.getLong("GlobalSoundDocId", 0);
                        } else if (currentType == NotificationsController.TYPE_GROUP) {
                            value = preferences.getString("GroupSound", LocaleController.getString("SoundDefault", R.string.SoundDefault));
                            documentId = preferences.getLong("GroupSoundDocId", 0);
                        } else {
                            value = preferences.getString("ChannelSound", LocaleController.getString("SoundDefault", R.string.SoundDefault));
                            documentId = preferences.getLong("ChannelDocId", 0);
                        }
                        if (documentId != 0) {
                            TLRPC.Document document = getMediaDataController().ringtoneDataStore.getDocument(documentId);
                            if (document == null) {
                                value = LocaleController.getString("CustomSound", R.string.CustomSound);
                            } else {
                                value = NotificationsSoundActivity.trimTitle(document, FileLoader.getDocumentFileName(document));
                            }
                        } else if (value.equals("NoSound")) {
                            value = LocaleController.getString("NoSound", R.string.NoSound);
                        } else if (value.equals("Default")) {
                            value = LocaleController.getString("SoundDefault", R.string.SoundDefault);
                        }
                        textCell.setTextAndValue(LocaleController.getString("Sound", R.string.Sound), value, true);
                    } else if (position == messageVibrateRow) {
                        int value;
                        if (currentType == NotificationsController.TYPE_PRIVATE) {
                            value = preferences.getInt("vibrate_messages", 0);
                        } else if (currentType == NotificationsController.TYPE_GROUP) {
                            value = preferences.getInt("vibrate_group", 0);
                        } else {
                            value = preferences.getInt("vibrate_channel", 0);
                        }
                        if (value == 0) {
                            textCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("VibrationDefault", R.string.VibrationDefault), true);
                        } else if (value == 1) {
                            textCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("Short", R.string.Short), true);
                        } else if (value == 2) {
                            textCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("VibrationDisabled", R.string.VibrationDisabled), true);
                        } else if (value == 3) {
                            textCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("Long", R.string.Long), true);
                        } else if (value == 4) {
                            textCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("OnlyIfSilent", R.string.OnlyIfSilent), true);
                        }
                    } else if (position == messagePriorityRow) {
                        int value;
                        if (currentType == NotificationsController.TYPE_PRIVATE) {
                            value = preferences.getInt("priority_messages", 1);
                        } else if (currentType == NotificationsController.TYPE_GROUP) {
                            value = preferences.getInt("priority_group", 1);
                        } else {
                            value = preferences.getInt("priority_channel", 1);
                        }
                        if (value == 0) {
                            textCell.setTextAndValue(LocaleController.getString("NotificationsImportance", R.string.NotificationsImportance), LocaleController.getString("NotificationsPriorityHigh", R.string.NotificationsPriorityHigh), false);
                        } else if (value == 1 || value == 2) {
                            textCell.setTextAndValue(LocaleController.getString("NotificationsImportance", R.string.NotificationsImportance), LocaleController.getString("NotificationsPriorityUrgent", R.string.NotificationsPriorityUrgent), false);
                        } else if (value == 4) {
                            textCell.setTextAndValue(LocaleController.getString("NotificationsImportance", R.string.NotificationsImportance), LocaleController.getString("NotificationsPriorityLow", R.string.NotificationsPriorityLow), false);
                        } else if (value == 5) {
                            textCell.setTextAndValue(LocaleController.getString("NotificationsImportance", R.string.NotificationsImportance), LocaleController.getString("NotificationsPriorityMedium", R.string.NotificationsPriorityMedium), false);
                        }
                    } else if (position == messagePopupNotificationRow) {
                        int option;
                        if (currentType == NotificationsController.TYPE_PRIVATE) {
                            option = preferences.getInt("popupAll", 0);
                        } else if (currentType == NotificationsController.TYPE_GROUP) {
                            option = preferences.getInt("popupGroup", 0);
                        } else {
                            option = preferences.getInt("popupChannel", 0);
                        }
                        String value;
                        if (option == 0) {
                            value = LocaleController.getString("NoPopup", R.string.NoPopup);
                        } else if (option == 1) {
                            value = LocaleController.getString("OnlyWhenScreenOn", R.string.OnlyWhenScreenOn);
                        } else if (option == 2) {
                            value = LocaleController.getString("OnlyWhenScreenOff", R.string.OnlyWhenScreenOff);
                        } else {
                            value = LocaleController.getString("AlwaysShowPopup", R.string.AlwaysShowPopup);
                        }
                        textCell.setTextAndValue(LocaleController.getString("PopupNotification", R.string.PopupNotification), value, true);
                    }
                    break;
                }
                case 6: {
                    NotificationsCheckCell checkCell = (NotificationsCheckCell) holder.itemView;
<<<<<<< HEAD
                    checkCell.setDrawLine(false);
                    String text;
                    StringBuilder builder = new StringBuilder();
                    int offUntil;
                    SharedPreferences preferences = getNotificationsSettings();

                    if (currentType == NotificationsController.TYPE_PRIVATE) {
                        text = LocaleController.getString("NotificationsForPrivateChats", R.string.NotificationsForPrivateChats);
                        offUntil = preferences.getInt("EnableAll2", 0);
                    } else if (currentType == NotificationsController.TYPE_GROUP) {
                        text = LocaleController.getString("NotificationsForGroups", R.string.NotificationsForGroups);
                        offUntil = preferences.getInt("EnableGroup2", 0);
                    } else {
                        text = LocaleController.getString("NotificationsForChannels", R.string.NotificationsForChannels);
                        offUntil = preferences.getInt("EnableChannel2", 0);
                    }
                    int currentTime = getConnectionsManager().getCurrentTime();
                    boolean enabled;
                    int iconType;
                    if (enabled = offUntil < currentTime) {
                        builder.append(LocaleController.getString("NotificationsOn", R.string.NotificationsOn));
                        iconType = 0;
                    } else if (offUntil - 60 * 60 * 24 * 365 >= currentTime) {
                        builder.append(LocaleController.getString("NotificationsOff", R.string.NotificationsOff));
                        iconType = 0;
                    } else {
                        builder.append(LocaleController.formatString("NotificationsOffUntil", R.string.NotificationsOffUntil, LocaleController.stringForMessageListDate(offUntil)));
                        iconType = 2;
                    }
                    checkCell.setTextAndValueAndCheck(text, builder, enabled, iconType, false);
=======
                    checkCell.setDrawLine(true);
                    checkCell.setChecked(item.checked);
                    checkCell.setTextAndValueAndIconAndCheck(item.text, item.text2, item.resId, item.checked, 0, false, divider, true);
>>>>>>> d494ea8cb (update to 10.12.0 (4710))
                    break;
                }
                case 7: {
                    TextCell textCell = (TextCell) holder.itemView;
                    if (position == exceptionsAddRow) {
                        textCell.setTextAndIcon(LocaleController.getString("NotificationsAddAnException", R.string.NotificationsAddAnException), R.drawable.msg_contact_add, exceptionsStartRow != -1);
                        textCell.setColors(Theme.key_windowBackgroundWhiteBlueIcon, Theme.key_windowBackgroundWhiteBlueButton);
                    } else if (position == deleteAllRow) {
                        textCell.setText(LocaleController.getString("NotificationsDeleteAllException", R.string.NotificationsDeleteAllException), false);
                        textCell.setColors(-1, Theme.key_text_RedRegular);
                    }
                    break;
                }
                case VIEW_TYPE_EXPAND: {
                    ExpandView textCell = (ExpandView) holder.itemView;
                    textCell.setColors(Theme.key_windowBackgroundWhiteBlueIcon, Theme.key_windowBackgroundWhiteBlueButton);
                    textCell.set(item.text, item.resId == 1, divider);
                    break;
                }
            }
        }

        @Override
        public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
            if (exceptions == null || !exceptions.isEmpty()) {
                return;
            }
<<<<<<< HEAD
            boolean enabled = getNotificationsController().isGlobalNotificationsEnabled(currentType);
=======
            final boolean globalEnabled;
            if (currentType == TYPE_STORIES) {
                globalEnabled = storiesEnabled == null || storiesEnabled || exceptions != null && !exceptions.isEmpty();;
            } else {
                globalEnabled = getNotificationsController().isGlobalNotificationsEnabled(currentType);
            }
            final int position = holder.getAdapterPosition();
            ItemInner item = null;
            if (position >= 0 && position < items.size()) {
                item = items.get(position);
            }
            final boolean enabled;
            if (item != null && item.id == BUTTON_IMPORTANT_STORIES) {
                return;
//                enabled = storiesEnabled == null || !storiesEnabled;
            } else {
                enabled = globalEnabled;
            }
>>>>>>> d494ea8cb (update to 10.12.0 (4710))
            switch (holder.getItemViewType()) {
                case 0: {
                    HeaderCell headerCell = (HeaderCell) holder.itemView;
                    if (holder.getAdapterPosition() == messageSectionRow) {
                        headerCell.setEnabled(enabled, null);
                    } else {
                        headerCell.setEnabled(true, null);
                    }
                    break;
                }
                case 1: {
                    TextCheckCell textCell = (TextCheckCell) holder.itemView;
                    textCell.setEnabled(enabled, null);
                    break;
                }
                case 3: {
                    TextColorCell textCell = (TextColorCell) holder.itemView;
                    textCell.setEnabled(enabled, null);
                    break;
                }
                case 5: {
                    TextSettingsCell textCell = (TextSettingsCell) holder.itemView;
                    textCell.setEnabled(enabled, null);
                    break;
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == messageSectionRow) {
                return 0;
            } else if (position == previewRow) {
                return 1;
            } else if (position >= exceptionsStartRow && position < exceptionsEndRow) {
                return 2;
            } else if (position == messageLedRow) {
                return 3;
            } else if (position == groupSection2Row || position == alertSection2Row || position == exceptionsSection2Row || position == deleteAllSectionRow) {
                return 4;
            } else if (position == alertRow) {
                return 6;
            } else if (position == exceptionsAddRow || position == deleteAllRow) {
                return 7;
            } else {
                return 5;
            }
        }
    }

    public class ExpandView extends TextCell {
        public ImageView imageView;
        public ExpandView(Context context) {
            super(context);

            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_windowBackgroundWhiteBlueIcon), PorterDuff.Mode.SRC_IN));
            imageView.setImageResource(R.drawable.msg_expand);
            addView(imageView, LayoutHelper.createFrame(24, 24, Gravity.CENTER_VERTICAL | (LocaleController.isRTL ? Gravity.LEFT : Gravity.RIGHT), 17, 0, 17,0));
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            imageView.measure(widthMeasureSpec, heightMeasureSpec);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);

            int w = right - left;
            int l = LocaleController.isRTL ? dp(17) : w - dp(17 + 24);
            int t = (bottom - top - dp(24)) / 2;
            imageView.layout(l, t, l + dp(24), t + dp(24));
        }

        public void set(CharSequence text, boolean expand, boolean divider) {
            setArrow(expand, true);
            setText(text, divider);
        }

        public void setArrow(boolean expand, boolean animated) {
            if (animated) {
                imageView.animate().rotation(expand ? 0 : 180).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).setDuration(340).start();
            } else {
                imageView.setRotation(expand ? 0 : 180);
            }
        }

    }

    @Override
    public ArrayList<ThemeDescription> getThemeDescriptions() {
        ArrayList<ThemeDescription> themeDescriptions = new ArrayList<>();

        ThemeDescription.ThemeDescriptionDelegate cellDelegate = () -> {
            if (listView != null) {
                int count = listView.getChildCount();
                for (int a = 0; a < count; a++) {
                    View child = listView.getChildAt(a);
                    if (child instanceof UserCell) {
                        ((UserCell) child).update(0);
                    }
                }
            }
        };

        themeDescriptions.add(new ThemeDescription(listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{HeaderCell.class, TextCheckCell.class, TextColorCell.class, TextSettingsCell.class, UserCell.class, NotificationsCheckCell.class}, null, null, null, Theme.key_windowBackgroundWhite));
        themeDescriptions.add(new ThemeDescription(fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray));

        themeDescriptions.add(new ThemeDescription(actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault));
        themeDescriptions.add(new ThemeDescription(listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault));
        themeDescriptions.add(new ThemeDescription(actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon));
        themeDescriptions.add(new ThemeDescription(actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle));
        themeDescriptions.add(new ThemeDescription(actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector));

        themeDescriptions.add(new ThemeDescription(listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector));

        themeDescriptions.add(new ThemeDescription(listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider));

        themeDescriptions.add(new ThemeDescription(listView, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlueHeader));

        themeDescriptions.add(new ThemeDescription(listView, 0, new Class[]{TextCheckCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
        themeDescriptions.add(new ThemeDescription(listView, 0, new Class[]{TextCheckCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2));
        themeDescriptions.add(new ThemeDescription(listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrack));
        themeDescriptions.add(new ThemeDescription(listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrackChecked));

        themeDescriptions.add(new ThemeDescription(listView, 0, new Class[]{UserCell.class}, new String[]{"imageView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayIcon));
        themeDescriptions.add(new ThemeDescription(listView, 0, new Class[]{UserCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
        themeDescriptions.add(new ThemeDescription(listView, 0, new Class[]{UserCell.class}, new String[]{"statusColor"}, null, null, cellDelegate, Theme.key_windowBackgroundWhiteGrayText));
        themeDescriptions.add(new ThemeDescription(listView, 0, new Class[]{UserCell.class}, new String[]{"statusOnlineColor"}, null, null, cellDelegate, Theme.key_windowBackgroundWhiteBlueText));
        themeDescriptions.add(new ThemeDescription(listView, 0, new Class[]{UserCell.class}, null, Theme.avatarDrawables, null, Theme.key_avatar_text));
        themeDescriptions.add(new ThemeDescription(null, 0, null, null, null, cellDelegate, Theme.key_avatar_backgroundRed));
        themeDescriptions.add(new ThemeDescription(null, 0, null, null, null, cellDelegate, Theme.key_avatar_backgroundOrange));
        themeDescriptions.add(new ThemeDescription(null, 0, null, null, null, cellDelegate, Theme.key_avatar_backgroundViolet));
        themeDescriptions.add(new ThemeDescription(null, 0, null, null, null, cellDelegate, Theme.key_avatar_backgroundGreen));
        themeDescriptions.add(new ThemeDescription(null, 0, null, null, null, cellDelegate, Theme.key_avatar_backgroundCyan));
        themeDescriptions.add(new ThemeDescription(null, 0, null, null, null, cellDelegate, Theme.key_avatar_backgroundBlue));
        themeDescriptions.add(new ThemeDescription(null, 0, null, null, null, cellDelegate, Theme.key_avatar_backgroundPink));

        themeDescriptions.add(new ThemeDescription(listView, 0, new Class[]{GraySectionCell.class}, new String[]{"textView"}, null, null, null, Theme.key_graySectionText));
        themeDescriptions.add(new ThemeDescription(listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{GraySectionCell.class}, null, null, null, Theme.key_graySection));

        themeDescriptions.add(new ThemeDescription(listView, 0, new Class[]{NotificationsCheckCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
        themeDescriptions.add(new ThemeDescription(listView, 0, new Class[]{NotificationsCheckCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2));
        themeDescriptions.add(new ThemeDescription(listView, 0, new Class[]{NotificationsCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrack));
        themeDescriptions.add(new ThemeDescription(listView, 0, new Class[]{NotificationsCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrackChecked));

        themeDescriptions.add(new ThemeDescription(listView, 0, new Class[]{TextColorCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText));

        themeDescriptions.add(new ThemeDescription(listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
        themeDescriptions.add(new ThemeDescription(listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteValueText));

        themeDescriptions.add(new ThemeDescription(listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow));

        themeDescriptions.add(new ThemeDescription(listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlueButton));
        themeDescriptions.add(new ThemeDescription(listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextCell.class}, new String[]{"textView"}, null, null, null, Theme.key_text_RedRegular));
        themeDescriptions.add(new ThemeDescription(listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextCell.class}, new String[]{"imageView"}, null, null, null, Theme.key_windowBackgroundWhiteBlueIcon));

        return themeDescriptions;
    }
}
