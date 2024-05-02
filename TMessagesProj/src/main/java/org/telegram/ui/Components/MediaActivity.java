package org.telegram.ui.Components;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.graphics.ColorUtils;

import com.exteragram.messenger.ExteraConfig;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.R;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BackDrawable;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Components.FloatingDebug.FloatingDebugController;
import org.telegram.ui.Components.FloatingDebug.FloatingDebugProvider;
import org.telegram.ui.Components.Paint.ShapeDetector;
import org.telegram.ui.ProfileActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MediaActivity extends BaseFragment implements SharedMediaLayout.SharedMediaPreloaderDelegate, FloatingDebugProvider {

    private SharedMediaLayout.SharedMediaPreloader sharedMediaPreloader;
    private TLRPC.ChatFull currentChatInfo;
    private long dialogId;
    private SimpleTextView nameTextView;
    ProfileActivity.AvatarImageView avatarImageView;

    SharedMediaLayout sharedMediaLayout;
    AudioPlayerAlert.ClippingTextViewSwitcher mediaCounterTextView;

    public MediaActivity(Bundle args, SharedMediaLayout.SharedMediaPreloader sharedMediaPreloader) {
        super(args);
        this.sharedMediaPreloader = sharedMediaPreloader;
    }

    @Override
    public boolean onFragmentCreate() {
        dialogId = getArguments().getLong("dialog_id");
        if (this.sharedMediaPreloader == null) {
            this.sharedMediaPreloader = new SharedMediaLayout.SharedMediaPreloader(this);
            this.sharedMediaPreloader.addDelegate(this);
        }
        return super.onFragmentCreate();
    }

    @Override
<<<<<<< HEAD
=======
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        getNotificationCenter().removeObserver(this, NotificationCenter.userInfoDidLoad);
        getNotificationCenter().removeObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
        getNotificationCenter().removeObserver(this, NotificationCenter.storiesEnabledUpdate);
        if (applyBulletin != null) {
            Runnable runnable = applyBulletin;
            applyBulletin = null;
            AndroidUtilities.runOnUIThread(runnable);
        }
    }

    @Override
    public void didReceivedNotification(int id, int account, Object... args) {
        if (id == NotificationCenter.userInfoDidLoad) {
            long uid = (long) args[0];
            if (uid == dialogId) {
                currentUserInfo = (TLRPC.UserFull) args[1];
                if (sharedMediaLayout != null) {
                    sharedMediaLayout.setUserInfo(currentUserInfo);
                }
            }
        } else if (id == NotificationCenter.currentUserPremiumStatusChanged || id == NotificationCenter.storiesEnabledUpdate) {

        }
    }

    @Override
>>>>>>> d494ea8cb (update to 10.12.0 (4710))
    public View createView(Context context) {
        actionBar.setBackButtonDrawable(new BackDrawable(false));
        actionBar.setCastShadows(false);
        actionBar.setAddToContainer(false);
        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                if (id == -1) {
                    finishFragment();
                }
            }
        });
        actionBar.setColorFilterMode(PorterDuff.Mode.SRC_IN);
        FrameLayout avatarContainer = new FrameLayout(context);
        SizeNotifierFrameLayout fragmentView = new SizeNotifierFrameLayout(context) {

            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                FrameLayout.LayoutParams lp = (LayoutParams) sharedMediaLayout.getLayoutParams();
                lp.topMargin = ActionBar.getCurrentActionBarHeight() + (actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0);

                lp = (LayoutParams) avatarContainer.getLayoutParams();
                lp.topMargin = actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0;
                lp.height = ActionBar.getCurrentActionBarHeight();

                int textTop = (ActionBar.getCurrentActionBarHeight() / 2 - AndroidUtilities.dp(22)) / 2 + AndroidUtilities.dp(!AndroidUtilities.isTablet() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 5);
                lp = (LayoutParams) nameTextView.getLayoutParams();
                lp.topMargin = textTop;

                textTop = ActionBar.getCurrentActionBarHeight() / 2 + (ActionBar.getCurrentActionBarHeight() / 2 - AndroidUtilities.dp(19)) / 2 - AndroidUtilities.dp(3);
                lp = (LayoutParams) mediaCounterTextView.getLayoutParams();
                lp.topMargin = textTop;

                lp = (LayoutParams) avatarImageView.getLayoutParams();
                lp.topMargin = (ActionBar.getCurrentActionBarHeight() - AndroidUtilities.dp(42)) / 2;
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }

            @Override
            public boolean dispatchTouchEvent(MotionEvent ev) {
                if (sharedMediaLayout != null && sharedMediaLayout.isInFastScroll()) {
                    return sharedMediaLayout.dispatchFastScrollEvent(ev);
                }
                if (sharedMediaLayout != null && sharedMediaLayout.checkPinchToZoom(ev)) {
                    return true;
                }
                return super.dispatchTouchEvent(ev);
            }

            @Override
            protected void drawList(Canvas blurCanvas, boolean top) {
                sharedMediaLayout.drawListForBlur(blurCanvas);
            }
        };
        fragmentView.needBlur = true;
        this.fragmentView = fragmentView;

        nameTextView = new SimpleTextView(context);

        nameTextView.setTextSize(18);
        nameTextView.setGravity(Gravity.LEFT);
        nameTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        nameTextView.setLeftDrawableTopPadding(-AndroidUtilities.dp(1.3f));
        nameTextView.setScrollNonFitText(true);
        nameTextView.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        avatarContainer.addView(nameTextView, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.LEFT | Gravity.TOP, 118, 0, 56, 0));

        avatarImageView = new ProfileActivity.AvatarImageView(context) {
            @Override
            public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
                super.onInitializeAccessibilityNodeInfo(info);
                if (getImageReceiver().hasNotThumb()) {
                    info.setText(LocaleController.getString("AccDescrProfilePicture", R.string.AccDescrProfilePicture));
                    info.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.ACTION_CLICK, LocaleController.getString("Open", R.string.Open)));
                    info.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.ACTION_LONG_CLICK, LocaleController.getString("AccDescrOpenInPhotoViewer", R.string.AccDescrOpenInPhotoViewer)));
                } else {
                    info.setVisibleToUser(false);
                }
            }
        };
        avatarImageView.getImageReceiver().setAllowDecodeSingleFrame(true);
        avatarImageView.setRoundRadius(ExteraConfig.getAvatarCorners(42));
        avatarImageView.setPivotX(0);
        avatarImageView.setPivotY(0);
        AvatarDrawable avatarDrawable = new AvatarDrawable();
        avatarDrawable.setProfile(true);

        avatarImageView.setImageDrawable(avatarDrawable);
        avatarContainer.addView(avatarImageView, LayoutHelper.createFrame(42, 42, Gravity.TOP | Gravity.LEFT, 64, 0, 0, 0));

<<<<<<< HEAD
        mediaCounterTextView = new AudioPlayerAlert.ClippingTextViewSwitcher(context) {
            @Override
            protected TextView createTextView() {
                TextView textView = new TextView(context);
                textView.setTextColor(Theme.getColor(Theme.key_player_actionBarSubtitle));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                textView.setSingleLine(true);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setGravity(Gravity.LEFT);
                return textView;
            }
        };
        avatarContainer.addView(mediaCounterTextView, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.LEFT | Gravity.TOP, 118, 0, 56, 0));
        sharedMediaLayout = new SharedMediaLayout(context, dialogId, sharedMediaPreloader, 0, null, currentChatInfo, false, this, new SharedMediaLayout.Delegate() {
=======
        selectedTextView = new AnimatedTextView(context, true, true, true);
        selectedTextView.setAnimationProperties(.4f, 0, 320, CubicBezierInterpolator.EASE_OUT_QUINT);
        selectedTextView.setTextSize(dp(20));
        selectedTextView.setGravity(Gravity.LEFT);
        selectedTextView.setTextColor(getThemedColor(Theme.key_windowBackgroundWhiteBlackText));
        selectedTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        avatarContainer.addView(selectedTextView, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.MATCH_PARENT, Gravity.FILL_HORIZONTAL | Gravity.CENTER_VERTICAL, 72 + (hasAvatar ? 48 : 0), -2, 72, 0));

        if (type == TYPE_STORIES) {
            tabsView = new StoriesTabsView(context, getResourceProvider());
            tabsView.setOnTabClick(i -> {
                sharedMediaLayout.scrollToPage(SharedMediaLayout.TAB_STORIES + i);
            });

            buttonContainer = new FrameLayout(context);
            buttonContainer.setPadding(dp(10), dp(8), dp(10), dp(8));
            buttonContainer.setBackgroundColor(getThemedColor(Theme.key_windowBackgroundWhite));
            button = new ButtonWithCounterView(context, getResourceProvider());
            button.setText(LocaleController.getString("SaveToProfile", R.string.SaveToProfile), false);
            button.setShowZero(true);
            button.setCount(0, false);
            button.setEnabled(false);
            button.setOnClickListener(v -> {
                if (applyBulletin != null) {
                    applyBulletin.run();
                    applyBulletin = null;
                }
                Bulletin.hideVisible();
                boolean pin = sharedMediaLayout.getClosestTab() == SharedMediaLayout.TAB_ARCHIVED_STORIES;
                int count = 0;
                ArrayList<TL_stories.StoryItem> storyItems = new ArrayList<>();
                if (actionModeMessageObjects != null) {
                    for (int i = 0; i < actionModeMessageObjects.size(); ++i) {
                        MessageObject messageObject = actionModeMessageObjects.valueAt(i);
                        if (messageObject.storyItem != null) {
                            storyItems.add(messageObject.storyItem);
                            count++;
                        }
                    }
                }
                sharedMediaLayout.closeActionMode(false);
                if (pin) {
                    sharedMediaLayout.scrollToPage(SharedMediaLayout.TAB_STORIES);
                }
                if (storyItems.isEmpty()) {
                    return;
                }
                boolean[] pastValues = new boolean[storyItems.size()];
                for (int i = 0; i < storyItems.size(); ++i) {
                    TL_stories.StoryItem storyItem = storyItems.get(i);
                    pastValues[i] = storyItem.pinned;
                    storyItem.pinned = pin;
                }
                getMessagesController().getStoriesController().updateStoriesInLists(dialogId, storyItems);
                final boolean[] undone = new boolean[] { false };
                applyBulletin = () -> {
                    getMessagesController().getStoriesController().updateStoriesPinned(dialogId, storyItems, pin, null);
                };
                final Runnable undo = () -> {
                    undone[0] = true;
                    AndroidUtilities.cancelRunOnUIThread(applyBulletin);
                    for (int i = 0; i < storyItems.size(); ++i) {
                        TL_stories.StoryItem storyItem = storyItems.get(i);
                        storyItem.pinned = pastValues[i];
                    }
                    getMessagesController().getStoriesController().updateStoriesInLists(dialogId, storyItems);
                };
                Bulletin bulletin;
                if (pin) {
                    bulletin = BulletinFactory.of(this).createSimpleBulletin(R.raw.contact_check, LocaleController.formatPluralString("StorySavedTitle", count), LocaleController.getString("StorySavedSubtitle"), LocaleController.getString("Undo"), undo).show();
                } else {
                    bulletin = BulletinFactory.of(this).createSimpleBulletin(R.raw.chats_archived, LocaleController.formatPluralString("StoryArchived", count), LocaleController.getString("Undo"), Bulletin.DURATION_PROLONG, undo).show();
                }
                bulletin.setOnHideListener(() -> {
                    if (!undone[0] && applyBulletin != null) {
                        applyBulletin.run();
                    }
                    applyBulletin = null;
                });
            });
            buttonContainer.addView(button);
            buttonContainer.setAlpha(0f);
            buttonContainer.setTranslationY(dp(100));

            Bulletin.addDelegate(this, new Bulletin.Delegate() {
                @Override
                public int getBottomOffset(int tag) {
                    return AndroidUtilities.dp(64);
                }
            });
        }

        if (type == TYPE_MEDIA && dialogId == getUserConfig().getClientUserId() && topicId == 0 && !getMessagesController().getSavedMessagesController().unsupported && getMessagesController().getSavedMessagesController().hasDialogs()) {
            initialTab = SharedMediaLayout.TAB_SAVED_DIALOGS;
        }
        sharedMediaLayout = new SharedMediaLayout(context, dialogId, sharedMediaPreloader, 0, null, currentChatInfo, currentUserInfo, initialTab, this, new SharedMediaLayout.Delegate() {
>>>>>>> d494ea8cb (update to 10.12.0 (4710))
            @Override
            public void scrollToSharedMedia() {

            }

            @Override
            public boolean onMemberClick(TLRPC.ChatParticipant participant, boolean b, boolean resultOnly, View vi) {
                return false;
            }

            @Override
            public TLRPC.Chat getCurrentChat() {
                return null;
            }

            @Override
            public boolean isFragmentOpened() {
                return true;
            }

            @Override
            public RecyclerListView getListView() {
                return null;
            }

            @Override
            public boolean canSearchMembers() {
                return false;
            }

            @Override
            public void updateSelectedMediaTabText() {
                updateMediaCount();
            }

        }, SharedMediaLayout.VIEW_TYPE_MEDIA_ACTIVITY, getResourceProvider()) {
            @Override
            protected void onSelectedTabChanged() {
                updateMediaCount();
            }

            @Override
            protected void onSearchStateChanged(boolean expanded) {
                AndroidUtilities.removeAdjustResize(getParentActivity(), classGuid);
                AndroidUtilities.updateViewVisibilityAnimated(avatarContainer, !expanded, 0.95f, true);
            }

            @Override
            protected void drawBackgroundWithBlur(Canvas canvas, float y, Rect rectTmp2, Paint backgroundPaint) {
                fragmentView.drawBlurRect(canvas, getY() + y, rectTmp2, backgroundPaint, true);
            }

            @Override
            protected void invalidateBlur() {
                fragmentView.invalidateBlur();
            }
<<<<<<< HEAD
=======

            @Override
            protected boolean isStoriesView() {
                return type == TYPE_STORIES || type == TYPE_ARCHIVED_CHANNEL_STORIES;
            }

            protected boolean customTabs() {
                return type == TYPE_STORIES || type == TYPE_ARCHIVED_CHANNEL_STORIES;
            }

            @Override
            protected boolean includeStories() {
                return type == TYPE_STORIES || type == TYPE_ARCHIVED_CHANNEL_STORIES;
            }

            @Override
            protected boolean includeSavedDialogs() {
                return type == TYPE_MEDIA && dialogId == getUserConfig().getClientUserId() && topicId == 0;
            }

            @Override
            protected boolean isArchivedOnlyStoriesView() {
                return type == TYPE_ARCHIVED_CHANNEL_STORIES;
            }

            @Override
            protected int getInitialTab() {
                return initialTab;
            }

            private AnimatorSet actionModeAnimation;

            @Override
            protected void showActionMode(boolean show) {
                if (type == TYPE_MEDIA) {
                    super.showActionMode(show);
                    return;
                }
                if (isActionModeShowed == show) {
                    return;
                }
                isActionModeShowed = show;
                if (actionModeAnimation != null) {
                    actionModeAnimation.cancel();
                }
                if (type == TYPE_STORIES || type == TYPE_ARCHIVED_CHANNEL_STORIES) {
                    disableScroll(show);
                }
                if (show) {
                    selectedTextView.setVisibility(VISIBLE);
                    if (buttonContainer != null) {
                        buttonContainer.setVisibility(VISIBLE);
                    }
                } else {
                    titlesContainer.setVisibility(VISIBLE);
                }
                backDrawable.setRotation(show ? 1f : 0f, true);
                actionModeAnimation = new AnimatorSet();
                ArrayList<Animator> animators = new ArrayList<>();
                animators.add(ObjectAnimator.ofFloat(selectedTextView, View.ALPHA, show ? 1.0f : 0.0f));
                animators.add(ObjectAnimator.ofFloat(titlesContainer, View.ALPHA, show ? 0.0f : 1.0f));
                if (buttonContainer != null) {
                    boolean showButton = show;
                    animators.add(ObjectAnimator.ofFloat(buttonContainer, View.ALPHA, showButton ? 1.0f : 0.0f));
                    animators.add(ObjectAnimator.ofFloat(buttonContainer, View.TRANSLATION_Y, showButton ? 0.0f : buttonContainer.getMeasuredHeight()));
                }
                if (deleteItem != null) {
                    deleteItem.setVisibility(View.VISIBLE);
                    animators.add(ObjectAnimator.ofFloat(deleteItem, View.ALPHA, show ? 1.0f : 0.0f));
                }
                final boolean empty = getStoriesCount(getClosestTab()) == 0;
                if (optionsItem != null) {
                    optionsItem.setVisibility(View.VISIBLE);
                    animators.add(ObjectAnimator.ofFloat(optionsItem, View.ALPHA, show || empty ? 0.0f : 1.0f));
                }
                if (tabsView != null) {
                    animators.add(ObjectAnimator.ofFloat(tabsView, View.ALPHA, show ? 0.4f : 1.0f));
                }
                actionModeAnimation.playTogether(animators);
                actionModeAnimation.setDuration(300);
                actionModeAnimation.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                actionModeAnimation.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        actionModeAnimation = null;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (actionModeAnimation == null) {
                            return;
                        }
                        actionModeAnimation = null;
                        if (!show) {
                            selectedTextView.setVisibility(INVISIBLE);
                            if (buttonContainer != null) {
                                buttonContainer.setVisibility(INVISIBLE);
                            }
                            if (deleteItem != null) {
                                deleteItem.setVisibility(View.GONE);
                            }
                            if (empty && optionsItem != null) {
                                optionsItem.setVisibility(View.GONE);
                            }
                        } else {
                            titlesContainer.setVisibility(INVISIBLE);
                            if (optionsItem != null) {
                                optionsItem.setVisibility(View.GONE);
                            }
                        }
                    }
                });
                actionModeAnimation.start();
            }

            @Override
            protected void onActionModeSelectedUpdate(SparseArray<MessageObject> messageObjects) {
                final int count = messageObjects.size();
                actionModeMessageObjects = messageObjects;
                if (type == TYPE_STORIES || type == TYPE_ARCHIVED_CHANNEL_STORIES) {
                    selectedTextView.cancelAnimation();
                    selectedTextView.setText(LocaleController.formatPluralString("StoriesSelected", count), !LocaleController.isRTL);
                    if (button != null) {
                        button.setEnabled(count > 0);
                        button.setCount(count, true);
                        if (sharedMediaLayout.getClosestTab() == SharedMediaLayout.TAB_STORIES) {
                            button.setText(LocaleController.formatPluralString("ArchiveStories", count), true);
                        }
                    }
                }
            }

            @Override
            protected void onTabProgress(float progress) {
                if (type != TYPE_STORIES)
                    return;
                float t = progress - TAB_STORIES;
                if (tabsView != null) {
                    tabsView.setProgress(t);
                }
                titles[0].setAlpha(1f - t);
                titles[0].setTranslationX(AndroidUtilities.dp(-12) * t);
                titles[1].setAlpha(t);
                titles[1].setTranslationX(AndroidUtilities.dp(12) * (1f - t));
            }

            @Override
            protected void onTabScroll(boolean scrolling) {
                if (tabsView != null) {
                    tabsView.setScrolling(scrolling);
                }
            }
>>>>>>> d494ea8cb (update to 10.12.0 (4710))
        };
        sharedMediaLayout.setPinnedToTop(true);
        sharedMediaLayout.getSearchItem().setTranslationY(0);
        sharedMediaLayout.photoVideoOptionsItem.setTranslationY(0);

        fragmentView.addView(sharedMediaLayout);
        fragmentView.addView(actionBar);
        fragmentView.addView(avatarContainer);
        fragmentView.blurBehindViews.add(sharedMediaLayout);

        TLObject avatarObject = null;
        if (DialogObject.isEncryptedDialog(dialogId)) {
            TLRPC.EncryptedChat encryptedChat = getMessagesController().getEncryptedChat(DialogObject.getEncryptedChatId(dialogId));
            if (encryptedChat != null) {
                TLRPC.User user = getMessagesController().getUser(encryptedChat.user_id);
                if (user != null) {
                    nameTextView.setText(ContactsController.formatName(user.first_name, user.last_name));
                    avatarDrawable.setInfo(user);
                    avatarObject = user;
                }
            }
        } else if (DialogObject.isUserDialog(dialogId)) {
            TLRPC.User user = MessagesController.getInstance(currentAccount).getUser(dialogId);
            if (user != null) {
                if (user.self) {
                    nameTextView.setText(LocaleController.getString("SavedMessages", R.string.SavedMessages));
                    avatarDrawable.setAvatarType(AvatarDrawable.AVATAR_TYPE_SAVED);
                    avatarDrawable.setScaleSize(.8f);
                } else {
                    nameTextView.setText(ContactsController.formatName(user.first_name, user.last_name));
                    avatarDrawable.setInfo(user);
                    avatarObject = user;
                }
            }

        } else {
            TLRPC.Chat chat = MessagesController.getInstance(currentAccount).getChat(-dialogId);
            if (chat != null) {
                nameTextView.setText(chat.title);
                avatarDrawable.setInfo(chat);
                avatarObject = chat;
            }
        }

        final ImageLocation thumbLocation = ImageLocation.getForUserOrChat(avatarObject, ImageLocation.TYPE_SMALL);
        avatarImageView.setImage(thumbLocation, "50_50", avatarDrawable, avatarObject);


        if (TextUtils.isEmpty(nameTextView.getText())) {
            nameTextView.setText(LocaleController.getString("SharedContentTitle", R.string.SharedContentTitle));
        }

        if (sharedMediaLayout.isSearchItemVisible()) {
            sharedMediaLayout.getSearchItem().setVisibility(View.VISIBLE);
        }
        if (sharedMediaLayout.isCalendarItemVisible()) {
            sharedMediaLayout.photoVideoOptionsItem.setVisibility(View.VISIBLE);
        } else {
            sharedMediaLayout.photoVideoOptionsItem.setVisibility(View.INVISIBLE);
        }

        actionBar.setDrawBlurBackground(fragmentView);
        AndroidUtilities.updateViewVisibilityAnimated(avatarContainer, true, 1, false);
        updateMediaCount();
        updateColors();
        return fragmentView;
    }

    @Override
    public boolean isSwipeBackEnabled(MotionEvent event) {
        if (!sharedMediaLayout.isSwipeBackEnabled()) {
            return false;
        }
        return sharedMediaLayout.isCurrentTabFirst();
    }

    @Override
    public boolean canBeginSlide() {
        if (!sharedMediaLayout.isSwipeBackEnabled()) {
            return false;
        }
        return super.canBeginSlide();
    }

    private void updateMediaCount() {
        int id = sharedMediaLayout.getClosestTab();
        int[] mediaCount = sharedMediaPreloader.getLastMediaCount();
        if (id < 0 || mediaCount[id] < 0) {
            return;
        }
        if (id == 0) {
            if (sharedMediaLayout.getPhotosVideosTypeFilter() == SharedMediaLayout.FILTER_PHOTOS_ONLY) {
                mediaCounterTextView.setText(LocaleController.formatPluralString("Photos", mediaCount[MediaDataController.MEDIA_PHOTOS_ONLY]));
            } else if (sharedMediaLayout.getPhotosVideosTypeFilter() == SharedMediaLayout.FILTER_VIDEOS_ONLY) {
                mediaCounterTextView.setText(LocaleController.formatPluralString("Videos", mediaCount[MediaDataController.MEDIA_VIDEOS_ONLY]));
            } else {
                mediaCounterTextView.setText(LocaleController.formatPluralString("Media", mediaCount[MediaDataController.MEDIA_PHOTOVIDEO]));
            }
        } else if (id == 1) {
            mediaCounterTextView.setText(LocaleController.formatPluralString("Files", mediaCount[MediaDataController.MEDIA_FILE]));
        } else if (id == 2) {
            mediaCounterTextView.setText(LocaleController.formatPluralString("Voice", mediaCount[MediaDataController.MEDIA_AUDIO]));
        } else if (id == 3) {
            mediaCounterTextView.setText(LocaleController.formatPluralString("Links", mediaCount[MediaDataController.MEDIA_URL]));
        } else if (id == 4) {
            mediaCounterTextView.setText(LocaleController.formatPluralString("MusicFiles", mediaCount[MediaDataController.MEDIA_MUSIC]));
        } else if (id == 5) {
            mediaCounterTextView.setText(LocaleController.formatPluralString("GIFs", mediaCount[MediaDataController.MEDIA_GIF]));
        }
    }

    public void setChatInfo(TLRPC.ChatFull currentChatInfo) {
        this.currentChatInfo = currentChatInfo;
    }

    public long getDialogId() {
        return dialogId;
    }

    @Override
    public void mediaCountUpdated() {
        if (sharedMediaLayout != null && sharedMediaPreloader != null) {
            sharedMediaLayout.setNewMediaCounts(sharedMediaPreloader.getLastMediaCount());
        }
        updateMediaCount();
    }


    private void updateColors() {
        actionBar.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        actionBar.setItemsColor(Theme.getColor(Theme.key_actionBarActionModeDefaultIcon), false);
        actionBar.setItemsBackgroundColor(Theme.getColor(Theme.key_actionBarActionModeDefaultSelector), false);
        actionBar.setTitleColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        nameTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
    }

    @Override
    public ArrayList<ThemeDescription> getThemeDescriptions() {
        ThemeDescription.ThemeDescriptionDelegate themeDelegate = () -> {
            updateColors();
        };
        ArrayList<ThemeDescription> arrayList = new ArrayList<>();
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDelegate, Theme.key_windowBackgroundWhite));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDelegate, Theme.key_actionBarActionModeDefaultSelector));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDelegate, Theme.key_windowBackgroundWhiteBlackText));
        arrayList.addAll(sharedMediaLayout.getThemeDescriptions());
        return arrayList;
    }

    @Override
    public boolean isLightStatusBar() {
        int color = Theme.getColor(Theme.key_windowBackgroundWhite);
        if (actionBar.isActionModeShowed()) {
            color = Theme.getColor(Theme.key_actionBarActionModeDefault);
        }
        return ColorUtils.calculateLuminance(color) > 0.7f;
    }

    @Override
    public List<FloatingDebugController.DebugItem> onGetDebugItems() {
        return Arrays.asList(
            new FloatingDebugController.DebugItem(
                (ShapeDetector.isLearning(getContext()) ? "Disable" : "Enable") + " shape detector learning debug",
                () -> {
                    ShapeDetector.setLearning(getContext(), !ShapeDetector.isLearning(getContext()));
                }
            )
        );
    }
}
