// nueva-cita.js
document.addEventListener('DOMContentLoaded', function() {
    // Establecer fecha mínima como la fecha actual
    const now = new Date();
    const localDateTime = now.toISOString().slice(0, 16);
    document.getElementById('fechaHora').min = localDateTime;
    
    // Establecer una fecha por defecto (próxima hora)
    const nextHour = new Date(now.getTime() + 60 * 60 * 1000);
    const nextHourString = nextHour.toISOString().slice(0, 16);
    document.getElementById('fechaHora').value = nextHourString;
    
    // Validación de formulario
    const form = document.querySelector('form');
    form.addEventListener('submit', function(e) {
        const servicioId = document.getElementById('servicioId').value;
        const profesionalId = document.getElementById('profesionalId').value;
        const fechaHora = document.getElementById('fechaHora').value;
        
        if (!servicioId || !profesionalId || !fechaHora) {
            e.preventDefault();
            alert('Por favor, completa todos los campos requeridos.');
            return;
        }
        
        // Validar que la fecha sea futura
        const selectedDate = new Date(fechaHora);
        if (selectedDate <= now) {
            e.preventDefault();
            alert('Por favor, selecciona una fecha y hora futura.');
            return;
        }
    });
    
    // Mostrar mensajes de éxito/error
    const successMessage = document.querySelector('.success-message');
    const errorMessage = document.querySelector('.error-message');
    
    if (successMessage || errorMessage) {
        window.scrollTo({ top: 0, behavior: 'smooth' });
        
        // Auto-ocultar mensajes después de 5 segundos
        setTimeout(() => {
            if (successMessage) {
                successMessage.style.display = 'none';
            }
            if (errorMessage) {
                errorMessage.style.display = 'none';
            }
        }, 5000);
    }
});