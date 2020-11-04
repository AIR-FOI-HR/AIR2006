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
    public class UlogaService : IUlogaService
    {
        protected GenericRepository<UlogaEntity> Repository { get; private set; }
        protected IMapper Mapper { get; private set; }
        private readonly UnitOfWork unitOfWork;

        public UlogaService(IMapper mapper)
        {
            unitOfWork = new UnitOfWork();
            Repository = unitOfWork.UlogaRepository;
            Mapper = mapper;
        }

        public async Task DeleteAsync(int id)
        {
            await Repository.DeleteAsync(id);
            await unitOfWork.SaveAsync();
        }

        public async Task<IEnumerable<IUlogaModel>> GetAsync(GetParams<IUlogaModel> getParams)
        {
            return Mapper.Map<ICollection<IUlogaModel>>(await Repository.Get(Mapper.Map<GetParams<UlogaEntity>>(getParams)));
        }

        public async Task<IUlogaModel> GetAsync(int id)
        {
            return Mapper.Map<IUlogaModel>(await Repository.GetByID(id));
        }

        public async Task InsertAsync(IUlogaModel entity)
        {
            Repository.Insert(Mapper.Map<UlogaEntity>(entity));
            await unitOfWork.SaveAsync();
        }

        public async Task UpdateAsync(IUlogaModel entity)
        {
            Repository.Update(Mapper.Map<UlogaEntity>(entity));
            await unitOfWork.SaveAsync();
        }
    }
}
