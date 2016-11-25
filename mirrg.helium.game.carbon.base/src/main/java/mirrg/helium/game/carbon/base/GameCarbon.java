package mirrg.helium.game.carbon.base;

import java.util.ArrayList;
import java.util.stream.Stream;

import com.thoughtworks.xstream.XStream;

import mirrg.helium.standard.hydrogen.event.EventManager;

public class GameCarbon<THIS extends GameCarbon<THIS, MODEL>, MODEL extends ModelCarbon<THIS>>
{

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

	private MODEL model;

	public synchronized MODEL getModel()
	{
		return model;
	}

	public synchronized void setModel(MODEL model)
	{
		event().post(new EventGameCarbon.ChangeModel.Pre());

		if (this.model != null) this.model.dispose();
		this.model = model;
		if (model != null) model.initialize(getThis());

		event().post(new EventGameCarbon.ChangeModel.Post());
	}

	//

	public XStream getXStream()
	{
		XStream xStream = new XStream();
		xStream.autodetectAnnotations(true);
		return xStream;
	}

	//

	private ArrayList<ControllerCarbon<? super THIS>> tools = new ArrayList<>();

	public void addTool(ControllerCarbon<? super THIS> tool)
	{
		tools.add(tool);
	}

	public Stream<ControllerCarbon<? super THIS>> getTools()
	{
		return tools.stream();
	}

}
