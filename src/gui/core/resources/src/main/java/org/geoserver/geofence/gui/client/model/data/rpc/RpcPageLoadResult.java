package it.geosolutions.geofence.gui.client.model.data.rpc;

import java.io.Serializable;
import java.util.List;

import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.IsSerializable;


public class RpcPageLoadResult<Data> extends BaseListLoadResult<Data> implements PagingLoadResult<Data>, Serializable,
    IsSerializable
{


    /**
     *
     */
    private static final long serialVersionUID = 8414050235263296544L;

    protected int offset = 0;
    protected int totalLength = 0;

    /**
     * Creates a new paging load result.
     *
     * @param data the data
     */
    public RpcPageLoadResult(List<Data> data)
    {
        super(data);
    }

    /**
     * Creates a new paging load result.
     *
     * @param data the data
     * @param offset the offset
     * @param totalLength the total length
     */
    public RpcPageLoadResult(List<Data> data, int offset, int totalLength)
    {
        this(data);
        this.offset = offset;
        this.totalLength = totalLength;
    }

    RpcPageLoadResult()
    {
        this(null);
    }

    public int getOffset()
    {
        return offset;
    }

    public int getTotalLength()
    {
        return totalLength;
    }

    public void setOffset(int offset)
    {
        this.offset = offset;
    }

    public void setTotalLength(int totalLength)
    {
        this.totalLength = totalLength;
    }

}
