// historial-citas.js
document.addEventListener('DOMContentLoaded', function() {
	// Auto-ocultar mensajes después de 5 segundos
	const successMessage = document.querySelector('.success-message');
	const errorMessage = document.querySelector('.error-message');

	if (successMessage || errorMessage) {
		// Scroll suave a mensajes
		window.scrollTo({ top: 0, behavior: 'smooth' });

		// Auto-ocultar después de 5 segundos
		setTimeout(() => {
			if (successMessage) {
				successMessage.style.opacity = '0';
				successMessage.style.transition = 'opacity 0.5s ease';
				setTimeout(() => successMessage.style.display = 'none', 500);
			}
			if (errorMessage) {
				errorMessage.style.opacity = '0';
				errorMessage.style.transition = 'opacity 0.5s ease';
				setTimeout(() => errorMessage.style.display = 'none', 500);
			}
		}, 5000);
	}

	// Manejar estado vacío - hacer scroll suave
	const emptyState = document.querySelector('.empty-state');
	if (emptyState && (citas == null || citas.length === 0)) {
		setTimeout(() => {
			emptyState.scrollIntoView({ behavior: 'smooth', block: 'center' });
		}, 1000);
	}
});