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
    public class ObjektService : IObjektService
    {
        protected GenericRepository<ObjektEntity> Repository { get; private set; }
        protected IMapper Mapper { get; private set; }
        private readonly UnitOfWork unitOfWork;

        public ObjektService(IMapper mapper)
        {
            unitOfWork = new UnitOfWork();
            Repository = unitOfWork.ObjektRepository;
            Mapper = mapper;
        }

        public async Task DeleteAsync(int id)
        {
            await Repository.DeleteAsync(id);
            await unitOfWork.SaveAsync();
        }

        public async Task<IEnumerable<IObjektModel>> GetAsync(GetParams<IObjektModel> getParams)
        {
            return Mapper.Map<ICollection<IObjektModel>>(await Repository.Get(Mapper.Map<GetParams<ObjektEntity>>(getParams)));
        }

        public async Task<IObjektModel> GetAsync(int id)
        {
            return Mapper.Map<IObjektModel>(await Repository.GetByID(id));
        }

        public async Task InsertAsync(IObjektModel entity)
        {
            Repository.Insert(Mapper.Map<ObjektEntity>(entity));
            await unitOfWork.SaveAsync();
        }

        public async Task UpdateAsync(IObjektModel entity)
        {
            Repository.Update(Mapper.Map<ObjektEntity>(entity));
            await unitOfWork.SaveAsync();
        }
    }
}
