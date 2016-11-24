package mirrg.helium.game.carbon.base;

import java.util.function.Consumer;

import mirrg.helium.standard.hydrogen.event.EventManager;

public abstract class EntityCarbon<G extends GameCarbon<?, ?>>
{

	protected final G game;

	public EntityCarbon(G game)
	{
		this.game = game;
	}

	//

	private boolean isDisposed = false;

	public void dispose()
	{
		isDisposed = true;
	}

	public boolean isDisposed()
	{
		return isDisposed;
	}

	//

	protected <T> void registerEvent(EventManager<? super T> eventManager, Class<T> clazz, Consumer<T> listener)
	{
		eventManager.registerRemovable(clazz, e -> {
			if (isDisposed) return false;

			listener.accept(e);

			return true;
		});
	}

	protected <T extends EventGameCarbon> void registerGameEvent(Class<T> clazz, Consumer<T> listener)
	{
		registerEvent(game.event(), clazz, listener);
	}

}
