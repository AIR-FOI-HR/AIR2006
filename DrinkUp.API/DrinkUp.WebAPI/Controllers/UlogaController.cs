using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using AutoMapper;
using DrinkUp.Common;
using DrinkUp.Common.Filter;
using DrinkUp.Common.Page;
using DrinkUp.Common.Sort;
using DrinkUp.Models;
using DrinkUp.Models.Common;
using DrinkUp.Service.Common;
using DrinkUp.WebAPI.REST;
using DrinkUp.WebAPI.ViewModels;
using Microsoft.AspNetCore.Mvc;

namespace DrinkUp.WebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UlogaController : ControllerBase
    {
        public IUlogaService Service { get; }
        public IMapper Mapper { get; }
        public IFilter Filter { get; }
        public ISort Sort { get; }
        public IPagedResult PagedResult { get; }

        public UlogaController(IUlogaService service, IMapper mapper, IFilter filter, ISort sort, IPagedResult pagedResult)
        {
            Service = service;
            Mapper = mapper;
            Filter = filter;
            Sort = sort;
            PagedResult = pagedResult;
        }

        [HttpGet]
        public async Task<ICollection<UlogaREST>> GetAsync(string filterColumn = "",
            string filterValue = "", int filterOption = 3, string sortBy = "",
            int sortOrder = 1, int pageSize = 10, int page = 1)
        {
            GetParams<UlogaModel> getParams = new GetParams<UlogaModel>()
            {
                PageNumber = page,
                PageSize = pageSize
            };
            FilterParams filterParams = new FilterParams()
            {
                ColumnName = filterColumn,
                FilterValue = filterValue,
                FilterOption = (FilterOptions)filterOption
            };
            SortingParams sortingParams = new SortingParams()
            {
                ColumnName = sortBy,
                SortOrder = (SortOrders)sortOrder
            };

            getParams.FilterParam = new[] { filterParams };
            getParams.SortingParam = new[] { sortingParams };
            getParams.Filter = Filter;
            getParams.Sort = Sort;
            getParams.Page = PagedResult;

            return Mapper.Map<List<UlogaREST>>(await Service.GetAsync(Mapper.Map<GetParams<IUlogaModel>>(getParams)));
        }

        [HttpGet("{id}")]
        public async Task<UlogaREST> GetAsync(int id)
        {
            return Mapper.Map<UlogaREST>(await Service.GetAsync(id));
        }

        [HttpPost("add")]
        public async Task<HttpResponseMessage> AddAsync(UlogaVM uloga)
        {
            try
            {
                await Service.InsertAsync(Mapper.Map<UlogaModel>(uloga));
            }
            catch
            {
                return new HttpResponseMessage(HttpStatusCode.BadRequest);
            }
            return new HttpResponseMessage(HttpStatusCode.Created);
        }

        [HttpPut("update")]
        public async Task<HttpResponseMessage> UpdateAsync(UlogaVM uloga)
        {
            try
            {
                await Service.UpdateAsync(Mapper.Map<UlogaModel>(uloga));
            }
            catch
            {
                return new HttpResponseMessage(HttpStatusCode.BadRequest);
            }
            return new HttpResponseMessage(HttpStatusCode.NoContent);
        }

        [HttpDelete("delete/{id}")]
        public async Task<HttpResponseMessage> DeleteAsync(int id)
        {
            try
            {
                await Service.DeleteAsync(id);
            }
            catch
            {
                return new HttpResponseMessage(HttpStatusCode.BadRequest);
            }
            return new HttpResponseMessage(HttpStatusCode.NoContent);
        }
    }
}
