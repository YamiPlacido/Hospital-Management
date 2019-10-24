(function ($, window, undefined) {
	"use strict";
		
	var Cookie = {
		create: function (name, value, days) {
			var expires;
			if (days) {
				var date = new Date();
				date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
				expires = "; expires=" + date.toGMTString();
			} else {
				expires = "";
			}
			document.cookie = name + "=" + value + expires + "; path=/";
		},
		read: function (name) {
			var nameEQ = name + "=",
				ca = document.cookie.split(';');
			for (var i = 0; i < ca.length; i++) {
				var c = ca[i];
				while (c.charAt(0) == ' ') c = c.substring(1, c.length);
				if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
			}
			return null;
		},
		erase: function (name) {
			this.create(name, "", -1);
		}
	};
	
	function Popup(options) {
		if (!(this instanceof Popup)) {
			return new Popup(options);
		}
		
		this.opts = options;
		this.init.call(this);
	}
	
	Popup.prototype = {
		init: function () {
			var that = this;

			$(document).on('click.pjd', '.pjd-popup-trigger', function (e) {
				if (e && e.preventDefault) {
					e.preventDefault();
				}
		        $(this).toggleClass('active');
		
		        var isClose = true;
		        if ($('.pjd-popup-ask-question').hasClass('active')) {
		            $('.pjd-popup').removeClass('active');
		        } else {
		            if ($('.pjd-popup-extend').hasClass('active')) {
		                $('.pjd-popup').removeClass('active');
		            } else {
		                $('.pjd-popup').removeClass('active');
		                $('.pjd-popup-ask-question').toggleClass('active');
		                isClose = false;
		            };
		        };
		        
		        if (isClose) {
		        	if (Cookie.read('pjd_' + that.opts.uuid) === null) {
		        		Cookie.create('pjd_' + that.opts.uuid, 1, 7);
		        	}
		        }
		
		        return false;
		    }).on('click.pjd', '.pjd-popup-extend-trigger', function (e) {
		    	if (e && e.preventDefault) {
					e.preventDefault();
				}
		    	
		    	var $form = $('#pjd-popup-extend-form'),
		    		name = $(this).data('name');
		    	
		    	$form
		    		.find(':checkbox')
		    		.prop('checked', false)
					.prop('disabled', false)
		    		.filter('[name="' + name + '"]')
		    		.prop('checked', true)
		    		.end().closest('.pjd-popup-form').show();
		    		
				if (name === 'is_unbranded') {
					$form.find('input[name="is_extended"]').prop('checked', true).prop('disabled', true);
				}
		
		        $('.pjd-popup-ask-question').removeClass('active');
		        $('.pjd-popup-extend').addClass('active');
		        
		        return false;
		    }).on('change.pjd', '.pjd-popup-checkbox', function (e) {
		    	var $this = $(this),
		    		$form = $this.closest('form');
		    	
		    	if ($this.attr('name') === 'is_unbranded') {
		    		if ($this.is(':checked')) {
			    		$form.find('input[name="is_extended"]').prop('checked', true).prop('disabled', true);
			    	} else {
			    		$form.find('input[name="is_extended"]').prop('disabled', false);
			    	}
		    	}
		    }).on('click.pjd', '.pjd-popup-extend-cancel', function (e) {
		    	if (e && e.preventDefault) {
					e.preventDefault();
				}
		
		        $('.pjd-popup-ask-question').addClass('active');
		        $('.pjd-popup-extend').removeClass('active');
		        
		        return false;
		    })/*.on('click.pjd', '.pjd-popup-form-trigger', function (e) {
		    	if (e && e.preventDefault) {
					e.preventDefault();
				}
		        $(this).addClass('hidden');
		
		        $('.pjd-popup-form, .pjd-popup-body-inner').addClass('active');
		
		        return false;
		    }).on('click.pjd', '.pjd-popup-form-cancel', function (e) {
		    	if (e && e.preventDefault) {
					e.preventDefault();
				}
		        $('.pjd-popup-form, .pjd-popup-body-inner').removeClass('active');
		        $('.pjd-popup-form-trigger').removeClass('hidden');
		
		        return false;
		    })*/.on('focusout.pjd', '#pjd-popup-ask-question-form .pjd-popup-field', function (e) {
		    	that.validateInput.call(that, this);
		    }).on('submit.pjd', '#pjd-popup-ask-question-form', function (e) {
		    	if (e && e.preventDefault) {
					e.preventDefault();
				}
		    	var errorClass = 'pjd-popup-field-error',
		    		isValid = true,
		    		$form = $(this),
		    		$name = $form.find('input[name="name"]'),
		    		$email = $form.find('input[name="email"]'),
		    		$question = $form.find('textarea[name="question"]');
		    	
		    	if (!$name.length || !that.validateInput.call(that, $name.get(0))) {
	    			isValid = false;
		    	}
		    	if (!$email.length || !that.validateInput.call(that, $email.get(0))) {
	    			isValid = false;
		    	}
		    	if (!$question.length || !that.validateInput.call(that, $question.get(0))) {
	    			isValid = false;
		    	}
		    	if (!isValid) {
		    		return false;
		    	}
	    		$form.find(':button').prop('disabled', true);
	    		
	    		var fd = new FormData(this);
	    		
	    		$.ajax({
	    			type: 'POST',
	    			url: [that.opts.url, 'popup.php?uuid=', that.opts.uuid, '&session_id=', that.opts.session_id].join(''), 
	    			data: fd,
	    			processData: false,
	    			contentType: false
	    		}).done(function (data) {
	    			if (data && data.status && data.status === 'OK') {
		    			$form.find(':input.pjd-popup-field, :file').val('');
		    			$form.find(':file').next('label').find('span').empty();
		    			
		    			$('.pjd-popup-form, .pjd-popup-body-inner').removeClass('active');
				        $('.pjd-popup-form').hide();
				        
				        var $open = $('.pjd-popup-open-support-ticket');
				        $open.attr('href', $open.attr('href').replace(/\{TicketID\}/, data.uuid)).addClass('active');
				        
				        if (data.uuid) {
		    				$('#pjd-popup-extend-form').find('input[name="ticket_uuid"]').val(data.uuid);
		    			}
		    		}
	    		}).always(function () {
		    		$form.find(':button').prop('disabled', false);
		    	});
		        return false;
		    }).on('focusout.pjd', '#pjd-popup-extend-form input[name="name"]', function (e) {
		    	that.validateInput.call(that, this);
		    }).on('submit.pjd', '#pjd-popup-extend-form', function (e) {
		    	if (e && e.preventDefault) {
					e.preventDefault();
				}
		    	var errorClass = 'pjd-popup-field-error',
		    		isValid = true,
		    		$form = $(this),
		    		$email = $form.find('input[name="email"]');

		    	if (!$form.find('.pjd-popup-checkbox:checked').length) {
		    		isValid = false;
		    	}
		    	if (!$email.length || !that.validateInput.call(that, $email.get(0))) {
	    			isValid = false;
		    	}
		    	if (!isValid) {
		    		return false;
		    	}
	    		$form.find(':button').prop('disabled', true);
		    	$.post([that.opts.url, 'popup.php?uuid=', that.opts.uuid, '&session_id=', that.opts.session_id].join(''), $form.serialize()).done(function (data) {
		    		if (data && data.status && data.status === 'OK') {
		    			var $inner = $('.pjd-popup-body-inner'),
		    				$extended = $form.find('input[name="is_extended"]'),
		    				$unbranded = $form.find('input[name="is_unbranded"]');
		    			
		    			if ($extended.length && $extended.is(':checked')) {
		    				$inner.find('.pjd-popup-extended').remove();
		    			}
		    			if ($unbranded.length && $unbranded.is(':checked')) {
		    				$inner.find('.pjd-popup-extended, .pjd-popup-unbranded').remove();
		    			}
		    			$form.find(':input.pjd-popup-field').val('');
		    			$form.closest('.pjd-popup-form').html('Please, check your email box and follow the instructions.');//FIXME
		    		}
		    	}).always(function () {
		    		$form.find(':button').prop('disabled', false);
		    	});
		    	return false;
		    }).on('change.pjd', '.pjd-inputfile', function (e) {
		    	var fileName = '',
		    		$label = $(this).next('label'),
		    		labelVal = $label.html();
		    	
				if (this.files && this.files.length > 1) {
					fileName = (this.getAttribute('data-multiple-caption') || '').replace('{count}', this.files.length);
				} else if (e.target.value) {
					fileName = e.target.value.split('\\').pop();
				}
				
				if (fileName) {
					$label.find('span').html(fileName);
				} else {
					$label.html(labelVal);
				}
		    }).on('focus.pjd', '.pjd-inputfile', function (e) {
		    	$(this).addClass('has-focus');
		    }).on('blur.pjd', '.pjd-inputfile', function (e) {
		    	$(this).removeClass('has-focus');
		    }).on("click", ".pjd-mega-sale-trigger", function (e) {
		    	$.post([that.opts.url, 'popup.php?uuid=', that.opts.uuid, '&session_id=', that.opts.session_id].join(''), {
		    		"do_mega_sale": 1
		    	});
		    });
			
			$.get([that.opts.url, 'popup.php'].join(''), {
				uuid: this.opts.uuid,
				session_id: this.opts.session_id
			}).done(function (data) {
				$('body').append(data);
				
				if (Cookie.read('pjd_' + that.opts.uuid) === null) {
					$('.pjd-popup-trigger').trigger('click.pjd');
				}
			});
		},
		isEmpty: function (input) {
			if (input.value === '') {
				return true;
			}
			return false;
		},
		validateInput: function (input) {
			var isEmpty = this.isEmpty.call(this, input);
			if (isEmpty) {
				$(input).addClass('pjd-popup-field-error');
			} else {
				$(input).removeClass('pjd-popup-field-error');
			}
			
			return !isEmpty;
		}
	};
	
	window.pjPopup = Popup;
})(jQuery, window);