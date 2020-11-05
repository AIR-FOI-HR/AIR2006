namespace DrinkUp.Models.Common
{
    public interface IAktivacijaObjektaModel
    {
        int Id { get; set; }
        int ObjektId { get; set; }
        string KodId { get; set; }

        IKodModel Kod { get; set; }
        IObjektModel Objekt { get; set; }
    }
}