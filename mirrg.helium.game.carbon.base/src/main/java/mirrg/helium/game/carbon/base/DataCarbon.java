package mirrg.helium.game.carbon.base;

import java.util.function.Consumer;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class DataCarbon<G extends GameCarbon<?, ?>>
{

	@XStreamOmitField
	private EntityCarbon<G> entity;

	public EntityCarbon<G> getEntity()
	{
		return entity;
	}

	protected EntityCarbon<G> createEntity(G game)
	{
		return null;
	}

	//

	public void getChildren(Consumer<DataCarbon<? super G>> dest)
	{

	}

	/**
	 * XStreamによって生成された直後に呼び出されなければならない。
	 */
	public void initialize(G game)
	{
		getChildren(d -> d.initialize(game));
		entity = createEntity(game);
	}

	public void dispose()
	{
		if (entity != null) entity.dispose();
		getChildren(d -> d.dispose());
	}

	public void getEntities(Consumer<EntityCarbon<? super G>> dest)
	{
		if (entity != null) dest.accept(entity);
		getChildren(d -> d.getEntities(e -> dest.accept(e)));
	}

}
