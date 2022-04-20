package BusinessLayer.DataObject;

import BusinessLayer.Types.ShippingZone;

public class Product
{
    public final int SupplierId, CompanyId, ItemId;

    public Product(int SupplierId, int CompanyId, int ItemId)
    {
        this.SupplierId = SupplierId;
        this.CompanyId = CompanyId;
        this.ItemId = ItemId;
    }

    @Override
    public String toString()
    {
        return String.format("Product\nSupplierId: %d\nCompanyId: %d\nItemId: %d\n", SupplierId, CompanyId, ItemId);
    }


}
