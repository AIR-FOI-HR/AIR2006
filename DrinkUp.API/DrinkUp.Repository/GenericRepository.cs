using DrinkUp.Common;
using DrinkUp.DAL.Context;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.ChangeTracking;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Repository
{
    public class GenericRepository<TEntity> where TEntity : class
    {
        internal DrinkUpContext context;
        internal DbSet<TEntity> dbSet;

        public GenericRepository(DrinkUpContext context)
        {
            this.context = context;
            dbSet = context.Set<TEntity>();
        }

        public virtual async Task<IEnumerable<TEntity>> Get(GetParams<TEntity> getParams)
        {
            IQueryable<TEntity> query = dbSet;

            query = getParams.Filter.FilteredData(query, getParams.FilterParam);
            query = getParams.Sort.SortData(query, getParams.SortingParam);
            foreach (string include in getParams.Include.Replace(" ", string.Empty).Split(','))
            {
                query = query.Include(include);
            }
            return await getParams.Page.GetPagedAsync(query, getParams.PageNumber, getParams.PageSize);
        }

        public virtual async Task<IEnumerable<TEntity>> Get(IQueryable<TEntity> linq)
        {
            return await linq.ToListAsync();
        }

        public virtual async Task<TEntity> GetByID(object id)
        {
            return await dbSet.FindAsync(id);
        }

        public virtual EntityEntry<TEntity> Insert(TEntity entity)
        {
            return dbSet.Add(entity);
        }

        public virtual async Task DeleteAsync(object id)
        {
            TEntity entityToDelete = await dbSet.FindAsync(id);
            Delete(entityToDelete);
        }

        public virtual void Delete(TEntity entityToDelete)
        {
            if (context.Entry(entityToDelete).State == EntityState.Detached)
            {
                dbSet.Attach(entityToDelete);
            }
            dbSet.Remove(entityToDelete);
        }

        public virtual void Update(TEntity entityToUpdate)
        {
            dbSet.Attach(entityToUpdate);
            context.Entry(entityToUpdate).State = EntityState.Modified;
        }
    }
}
