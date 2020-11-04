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
    public class VrstaPonudeService : IVrstaPonudeService
    {
        protected GenericRepository<VrstaPonudeEntity> Repository { get; private set; }
        protected IMapper Mapper { get; private set; }
        private readonly UnitOfWork unitOfWork;

        public VrstaPonudeService(IMapper mapper)
        {
            unitOfWork = new UnitOfWork();
            Repository = unitOfWork.VrstaPonudeRepository;
            Mapper = mapper;
        }

        public async Task DeleteAsync(int id)
        {
            await Repository.DeleteAsync(id);
            await unitOfWork.SaveAsync();
        }

        public async Task<IEnumerable<IVrstaPonudeModel>> GetAsync(GetParams<IVrstaPonudeModel> getParams)
        {
            return Mapper.Map<ICollection<IVrstaPonudeModel>>(await Repository.Get(Mapper.Map<GetParams<VrstaPonudeEntity>>(getParams)));
        }

        public async Task<IVrstaPonudeModel> GetAsync(int id)
        {
            return Mapper.Map<IVrstaPonudeModel>(await Repository.GetByID(id));
        }

        public async Task InsertAsync(IVrstaPonudeModel entity)
        {
            Repository.Insert(Mapper.Map<VrstaPonudeEntity>(entity));
            await unitOfWork.SaveAsync();
        }

        public async Task UpdateAsync(IVrstaPonudeModel entity)
        {
            Repository.Update(Mapper.Map<VrstaPonudeEntity>(entity));
            await unitOfWork.SaveAsync();
        }
    }
}
