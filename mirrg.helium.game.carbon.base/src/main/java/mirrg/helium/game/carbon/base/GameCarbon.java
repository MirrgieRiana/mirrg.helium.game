package mirrg.helium.game.carbon.base;

import java.util.ArrayList;
import java.util.function.Consumer;

import com.thoughtworks.xstream.XStream;

import mirrg.helium.standard.hydrogen.event.EventManager;

public class GameCarbon<THIS extends GameCarbon<THIS, DATA>, DATA extends DataCarbon<THIS>>
{

	public GameCarbon(DATA data)
	{
		setData(data);
	}

	@SuppressWarnings("unchecked")
	private THIS getThis()
	{
		return (THIS) this;
	}

	//

	private EventManager<EventGameCarbon> eventManager = new EventManager<>();

	public EventManager<EventGameCarbon> event()
	{
		return eventManager;
	}

	//

	private DATA data;

	public DATA getData()
	{
		return data;
	}

	public void setData(DATA data)
	{
		event().post(new EventGameCarbon.ChangeData.Pre());

		data.initialize(getThis());
		DATA tmp = this.data;
		this.data = data;
		tmp.dispose();

		event().post(new EventGameCarbon.ChangeData.Post());
	}

	//

	public XStream getXStream()
	{
		XStream xStream = new XStream();
		xStream.autodetectAnnotations(true);
		return xStream;
	}

	//

	private ArrayList<EntityCarbon<? super THIS>> tools = new ArrayList<>();

	public void addTool(EntityCarbon<? super THIS> tool)
	{
		tools.add(tool);
	}

	//

	public void getEntities(Consumer<EntityCarbon<? super THIS>> dest)
	{
		tools.forEach(dest::accept);
		data.getEntities(dest);
	}

}
