using CompanyApi.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace CompanyApi.Controllers;

[Route("api/[controller]")]
[ApiController]
public class DepartmentsController : ControllerBase
{
    private readonly CompanyDbContext _context;

    public DepartmentsController(CompanyDbContext context)
    {
        _context = context;
    }

    [HttpGet]
    public async Task<ActionResult<IEnumerable<DepartmentDTO>>> GetDepartments()
    {
        return await _context.Departments
        .Select(d => DepartmentToDTO(d))
        .ToListAsync();
    }

    [HttpGet("{id}")]
    public async Task<ActionResult<DepartmentDTO>> GetDepartment(int id)
    {
        var department = await _context.Departments.FindAsync(id);

        if (department == null)
            return NotFound();
        return DepartmentToDTO(department);

    }

    [HttpPost]
    public async Task<ActionResult<DepartmentDTO>> PostDepartment(DepartmentDTO departmentDTO)
    {
        var department = DTOToDepartment(departmentDTO);

        _context.Departments.Add(department);
        await _context.SaveChangesAsync();

        return CreatedAtAction(
        nameof(GetDepartment),
        new { id = department.DepartmentId },
        DepartmentToDTO(department));

    }

    [HttpPut("{id}")]
    public async Task<IActionResult> PutDepartment(int id, DepartmentDTO departmentDTO)
    {
        if (id != departmentDTO.DepartmentId)
            return BadRequest();
        _context.Entry(DTOToDepartment(departmentDTO)).State = EntityState.Modified;

        try
        {
            await _context.SaveChangesAsync();
        }
        catch (DbUpdateConcurrencyException)
        {
            if (!DepartmentExists(id))
                return NotFound();
            throw;
        }

        return NoContent();

    }

    [HttpDelete("{id}")]
    public async Task<ActionResult<DepartmentDTO>> DeleteDepartment(int id)
    {
        var department = await _context.Departments.FindAsync(id);

        if (department == null)
            return NotFound();
        _context.Departments.Remove(department);
        await _context.SaveChangesAsync();

        return DepartmentToDTO(department);

    }

    private static DepartmentDTO DepartmentToDTO(Department department) =>
    new DepartmentDTO
    {
        DepartmentId = department.DepartmentId,
        Name = department.Name
    };

    private static Department DTOToDepartment(DepartmentDTO dto) =>
    new Department
    {
        DepartmentId = dto.DepartmentId,
        Name = dto.Name
    };

    private bool DepartmentExists(int id) =>
    _context.Departments.Any(d => d.DepartmentId == id);
}