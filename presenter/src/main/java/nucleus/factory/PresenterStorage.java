package nucleus.factory;

import java.util.HashMap;

import nucleus.presenter.Presenter;

/**
 * @author konmik
 *         <p>
 *         modifier thanatos
 *         <p>
 *         当系统回收或者横竖屏切换的时候,我们并不想presenter销毁,我们将它保存在这个单例之中
 */
public enum PresenterStorage {

    INSTANCE;

    private HashMap<String, Presenter> idToPresenter = new HashMap<>();
    private HashMap<Presenter, String> presenterToId = new HashMap<>();

    /**
     * Adds a presenter to the storage
     *
     * @param presenter a presenter to add
     */
    public void add(final Presenter presenter) {
        String id = presenter.getClass().getSimpleName() + "/" + System.nanoTime() + "/" + (int) (Math.random() * Integer.MAX_VALUE);
        idToPresenter.put(id, presenter);
        presenterToId.put(presenter, id);
        // 及时删除,避免内存泄露
        presenter.addOnDestroyListener(new Presenter.OnDestroyListener() {
            @Override
            public void onDestroy() {
                idToPresenter.remove(presenterToId.remove(presenter));
            }
        });
    }

    /**
     * Returns a presenter by id or null if such presenter does not exist.
     *
     * @param id  id of a presenter that has been received by calling {@link #getId(Presenter)}
     * @param <P> a type of presenter
     * @return a presenter or null
     */
    public <P> P getPresenter(String id) {
        //noinspection unchecked
        return (P) idToPresenter.get(id);
    }

    /**
     * Returns id of a given presenter.
     *
     * @param presenter a presenter to get id for.
     * @return if of the presenter.
     */
    public String getId(Presenter presenter) {
        return presenterToId.get(presenter);
    }

    /**
     * Removes all presenters from the storage.
     * Use this method for testing purposes only.
     */
    public void clear() {
        idToPresenter.clear();
        presenterToId.clear();
    }
}
