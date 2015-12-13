package cn.mopon.scenic.gateway.service;

import fr.irec.www.webserviceac.CheckTicket;
import fr.irec.www.webserviceac.CheckTicketResponse;
import fr.irec.www.webserviceac.InitControlPoint2;
import fr.irec.www.webserviceac.InitControlPoint2Response;
import fr.irec.www.webserviceac.KeepAlive;
import fr.irec.www.webserviceac.Passage;
import fr.irec.www.webserviceac.PassageResponse;
import fr.irec.www.webserviceac.TimeoutTicket;

public abstract interface IWSTicketService
{
  public abstract InitControlPoint2Response initControlPoint(InitControlPoint2 paramInitControlPoint2);

  public abstract String keepAlive(KeepAlive paramKeepAlive);

  public abstract CheckTicketResponse checkTicket(CheckTicket paramCheckTicket);

  public abstract PassageResponse passage(Passage paramPassage);

  public abstract boolean timeOutTicket(TimeoutTicket paramTimeoutTicket);
}

/* Location:           D:\TestCode\mopon\new-scenic-gateway\
 * Qualified Name:     cn.mopon.scenic.gateway.service.IWSTicketService
 * JD-Core Version:    0.6.2
 */