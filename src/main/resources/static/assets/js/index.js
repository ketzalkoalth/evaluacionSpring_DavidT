// dashboard.js
function cancelarCita(citaId) {
    if (confirm('¿Estás seguro de que deseas cancelar esta cita?')) {
        fetch('/citas/' + citaId + '/cancelar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            }
        })
        .then(response => {
            if (response.ok) {
                alert('Cita cancelada exitosamente');
                location.reload();
            } else {
                alert('Error al cancelar la cita');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error al cancelar la cita');
        });
    }
}

// Mostrar notificación si hay mensaje de éxito
document.addEventListener('DOMContentLoaded', function() {
    const successMessage = document.querySelector('.success-message');
    if (successMessage) {
        // Hacer scroll suave al mensaje
        successMessage.scrollIntoView({ behavior: 'smooth', block: 'start' });
        
        // Auto-ocultar después de 5 segundos
        setTimeout(() => {
            successMessage.style.opacity = '0';
            successMessage.style.transition = 'opacity 0.5s ease';
            setTimeout(() => {
                if (successMessage.parentNode) {
                    successMessage.parentNode.removeChild(successMessage);
                }
            }, 500);
        }, 5000);
    }
    
    // Manejar estado vacío
    const emptyState = document.querySelector('.empty-state');
    if (emptyState) {
        emptyState.scrollIntoView({ behavior: 'smooth', block: 'center' });
    }
});