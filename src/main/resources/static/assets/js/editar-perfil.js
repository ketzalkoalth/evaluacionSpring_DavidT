// editar-perfil.js
document.addEventListener('DOMContentLoaded', function() {
	const form = document.getElementById('perfilForm');
	const submitBtn = document.getElementById('submitBtn');
	const passwordInput = document.getElementById('password');
	const passwordStrength = document.getElementById('password-strength');

	// Validar fortaleza de contraseña
	function validarPassword() {
		const password = passwordInput.value;

		if (password === '') {
			passwordStrength.style.display = 'none';
			return;
		}

		passwordStrength.style.display = 'block';

		let strength = 'débil';
		let strengthClass = 'password-strength-weak';

		if (password.length >= 8) {
			strength = 'media';
			strengthClass = 'password-strength-medium';
		}

		if (password.length >= 10 && /[A-Z]/.test(password) && /[0-9]/.test(password)) {
			strength = 'fuerte';
			strengthClass = 'password-strength-strong';
		}

		passwordStrength.textContent = `Fortaleza: ${strength}`;
		passwordStrength.className = strengthClass;
	}

	// Validación del formulario
	form.addEventListener('submit', function(e) {
		e.preventDefault();

		// Validar campos requeridos
		const nombre = document.getElementById('nombre').value.trim();
		const email = document.getElementById('email').value.trim();
		const telefono = document.getElementById('telefono').value.trim();
		const password = document.getElementById('password').value;

		if (!nombre || !email || !telefono) {
			alert('Por favor, completa todos los campos requeridos.');
			return;
		}

		// Validar formato de email
		const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		if (!emailRegex.test(email)) {
			alert('Por favor, ingresa un email válido.');
			return;
		}

		// Validar longitud mínima de contraseña si se proporciona
		if (password && password.length < 6) {
			alert('La contraseña debe tener al menos 6 caracteres.');
			return;
		}

		// Mostrar estado de carga
		submitBtn.innerHTML = '⏳ Guardando...';
		submitBtn.disabled = true;
		form.classList.add('loading');

		// Enviar formulario después de validación
		setTimeout(() => {
			form.submit();
		}, 1000);
	});

	// Validar en tiempo real
	const inputs = form.querySelectorAll('input[required]');
	inputs.forEach(input => {
		input.addEventListener('blur', function() {
			if (this.value.trim() === '') {
				this.style.borderColor = '#F44336';
			} else {
				this.style.borderColor = '#4CAF50';
			}
		});

		input.addEventListener('input', function() {
			if (this.value.trim() !== '') {
				this.style.borderColor = '#4A90E2';
			}
		});
	});

	// Manejar mensajes flash
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

	// Inicializar validación de password
	passwordInput.addEventListener('input', validarPassword);
});