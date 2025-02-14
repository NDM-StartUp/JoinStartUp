package ndmstartup.joinstartup.Services.Implementations;

import ndmstartup.joinstartup.DTOs.GetPositionDTO;
import ndmstartup.joinstartup.Mappers.EmployeeMapper;
import ndmstartup.joinstartup.Mappers.PositionMapper;
import ndmstartup.joinstartup.Models.Position;
import ndmstartup.joinstartup.Models.StartUpEmployee;
import ndmstartup.joinstartup.Repositories.PositionRepository;
import ndmstartup.joinstartup.Repositories.StartUpEmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PositionServiceImplTest {

    @Mock
    private PositionRepository positionRepository;

    @Mock
    private PositionMapper positionMapper;

    @Mock
    private StartUpEmployeeRepository startUpEmployeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private PositionServiceImpl positionService;

    @Mock
    List<Position> positions;

    @Mock
    StartUpEmployee startUpEmployee;

    @BeforeEach
    void setUp() {
        Position position1 = new Position();
        Position position2 = new Position();
        position1.setId(1L);
        position2.setId(2L);
        position1.setName("Developer");
        position2.setName("Manager");

        positions = Arrays.asList(position1, position2);
    }

    @Test
    void getAllPositions_Success() {
        Mockito.when(positionRepository.findAll()).thenReturn(positions);
        Mockito.when(positionMapper.entityToDTO(positions.get(0))).thenReturn(new GetPositionDTO(1L, "Developer"));
        Mockito.when(positionMapper.entityToDTO(positions.get(1))).thenReturn(new GetPositionDTO(2L, "Manager"));

        List<GetPositionDTO> result = positionService.getAllPositions();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Developer", result.get(0).getName());
        Assertions.assertEquals("Manager", result.get(1).getName());

        Mockito.verify(positionRepository).findAll();
        Mockito.verify(positionMapper, Mockito.times(2)).entityToDTO(Mockito.any(Position.class));
    }

    @Test
    void getAllPositions_EmptyList() {
        Mockito.when(positionRepository.findAll()).thenReturn(Collections.emptyList());

        List<GetPositionDTO> result = positionService.getAllPositions();

        Assertions.assertTrue(result.isEmpty());
        Mockito.verify(positionRepository).findAll();
        Mockito.verify(positionMapper, Mockito.never()).entityToDTO(Mockito.any(Position.class));
    }

    @Test
    void getEmployeePositions_SuccessWithStartUpId() {
        Long startUpId = 1L;
        Long employeeId = 1L;

        Mockito.when(startUpEmployeeRepository.findByStartUpIdAndEmployeeId(startUpId, employeeId)).thenReturn(Optional.of(startUpEmployee));

        positionService.getEmployeePositions(startUpId, employeeId);

        Mockito.verify(startUpEmployeeRepository).findByStartUpIdAndEmployeeId(startUpId, employeeId);
        Mockito.verify(startUpEmployeeRepository, Mockito.never()).findByEmployeeId(employeeId);
        Mockito.verify(employeeMapper, Mockito.times(1)).entityToPositionDTO(Mockito.any(StartUpEmployee.class));

    }

    @Test
    void getEmployeePositions_SuccessWithoutStartUpId() {
        Long startUpId = null;
        Long employeeId = 1L;

        Mockito.when(startUpEmployeeRepository.findByEmployeeId(employeeId)).thenReturn(Optional.of(startUpEmployee));

        positionService.getEmployeePositions(startUpId, employeeId);

        Mockito.verify(startUpEmployeeRepository).findByEmployeeId(employeeId);

        Mockito.verify(startUpEmployeeRepository, Mockito.never()).findByStartUpIdAndEmployeeId(startUpId, employeeId);
        Mockito.verify(employeeMapper, Mockito.times(1)).entityToPositionDTO(Mockito.any(StartUpEmployee.class));

    }

    @Test
    void getEmployeePositions_ThrowsNotFoundException_WhenStartUpIdNotFound() {
        Long startUpId = 20L;
        Long employeeId = 1L;

        Mockito.when(startUpEmployeeRepository.findByStartUpIdAndEmployeeId(startUpId, employeeId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> positionService.getEmployeePositions(startUpId, employeeId));

        Assertions.assertEquals(exception.getMessage(), "No positions with start up id " + startUpId  +" and employee id " + employeeId);

    }

    @Test
    void getEmployeePositions_ThrowsNotFoundException_WhenEmployeeNotFound() {
        Long startUpId = null;
        Long employeeId = 1L;

        Mockito.when(startUpEmployeeRepository.findByEmployeeId(employeeId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> positionService.getEmployeePositions(startUpId, employeeId));

        Assertions.assertEquals(exception.getMessage(), "No positions with employee id " + employeeId);

    }
}