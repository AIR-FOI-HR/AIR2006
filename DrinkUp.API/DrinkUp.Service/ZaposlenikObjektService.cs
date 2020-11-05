using AutoMapper;
using DrinkUp.Common;
using DrinkUp.DAL.Entities;
using DrinkUp.Models.Common;
using DrinkUp.Repository;
using DrinkUp.Service.Common;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Service
{
    public class ZaposlenikObjektService : IZaposlenikObjektService
    {
        protected GenericRepository<ZaposlenikObjekt> Repository { get; private set; }
        protected IMapper Mapper { get; private set; }
        private readonly UnitOfWork unitOfWork;

        public ZaposlenikObjektService(IMapper mapper)
        {
            unitOfWork = new UnitOfWork();
            Repository = unitOfWork.ZaposlenikObjektRepository;
            Mapper = mapper;
        }

        public async Task DeleteAsync(int id)
        {
            await Repository.DeleteAsync(id);
            await unitOfWork.SaveAsync();
        }

        public async Task<IEnumerable<IZaposlenikObjektModel>> GetAsync(GetParams<IZaposlenikObjektModel> getParams)
        {
            return Mapper.Map<ICollection<IZaposlenikObjektModel>>(await Repository.Get(Mapper.Map<GetParams<ZaposlenikObjekt>>(getParams)));
        }

        public async Task<IZaposlenikObjektModel> GetAsync(int id)
        {
            return Mapper.Map<IZaposlenikObjektModel>(await Repository.GetByID(id));
        }

        public async Task InsertAsync(IZaposlenikObjektModel entity)
        {
            Repository.Insert(Mapper.Map<ZaposlenikObjekt>(entity));
            await unitOfWork.SaveAsync();
        }

        public async Task UpdateAsync(IZaposlenikObjektModel entity)
        {
            Repository.Update(Mapper.Map<ZaposlenikObjekt>(entity));
            await unitOfWork.SaveAsync();
        }
    }
}
